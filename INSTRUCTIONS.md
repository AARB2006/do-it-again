# JDBC Transactions & Isolation Levels

Practice instructions for working with transactions and concurrency control in this JDBC CLI project.

---

## Background — What Is a Transaction?

A transaction is a group of SQL operations that are treated as a single unit. Either **all of them succeed** (commit) or **all of them fail** (rollback). You already use this in `addFilm`, `updateFilm`, and `deleteFilm`:

```java
conn.setAutoCommit(false);
// ... execute statements ...
conn.commit();    // success
conn.rollback();  // on failure
```

Isolation levels control what a transaction is **allowed to see** from other concurrent transactions.

---

## The 4 Concurrency Problems

### 1. Lost Update
Two transactions read the same film, then both write to it. The second write silently overwrites the first.

**Example in this project:**  
Two CLI sessions both call `updateFilm()` on the same film ID at the same time. Both read `rental_rate = 2.99`. Session A updates it to `3.99`. Session B then updates it to `1.99` — Session A's change is **gone**.

**Fix:** Use `SERIALIZABLE`. The second transaction will be blocked until the first finishes.

---

### 2. Dirty Read
Transaction A reads data that Transaction B wrote but **has not committed yet**. If B rolls back, A has read data that never officially existed.

**Example in this project:**  
Session B calls `addFilm()` — the `INSERT` runs but `conn.commit()` hasn't been called yet. Session A calls `findByTitle()` and sees that half-inserted film. Session B then hits an error and calls `conn.rollback()` — the film is gone, but Session A already showed it to the user.

**Fix:** Any isolation level at or above `READ COMMITTED` prevents this. MySQL's default (`REPEATABLE READ`) already covers you here.

---

### 3. Non-Repeatable Read
You read the same row **twice** inside one transaction and get different values because another transaction updated it in between.

**Example in this project:**  
You call `findByID(5)` to display a film to the user before they confirm an update. They confirm. You call `findByID(5)` again to validate the data — but another session already called `updateFilm()` on film 5. The values you showed the user are now stale.

**Fix:** Use `REPEATABLE READ` or `SERIALIZABLE`.

---

### 4. Phantom Read
You run the **same query twice** inside one transaction and get a different set of rows because another transaction inserted or deleted matching rows in between.

**Example in this project:**  
You call `findByRating("PG", 100)` to count PG films, do some logic, then call it again to confirm — but another session ran `addFilm()` with rating `"PG"` between your two calls. Now there's an extra "phantom" row you didn't account for.

**Fix:** Only `SERIALIZABLE` fully and reliably prevents this.

---

## Isolation Levels — Quick Reference

| Isolation Level | Dirty Read | Non-Repeatable Read | Phantom Read | JDBC Constant |
|---|---|---|---|---|
| `READ UNCOMMITTED` | ✅ can happen | ✅ can happen | ✅ can happen | `TRANSACTION_READ_UNCOMMITTED` |
| `READ COMMITTED` | ❌ prevented | ✅ can happen | ✅ can happen | `TRANSACTION_READ_COMMITTED` |
| `REPEATABLE READ` *(MySQL default)* | ❌ | ❌ | ✅ can happen* | `TRANSACTION_REPEATABLE_READ` |
| **`SERIALIZABLE`** | ❌ | ❌ | ❌ **prevented** | **`TRANSACTION_SERIALIZABLE`** |

> \* MySQL's InnoDB prevents phantoms at `REPEATABLE READ` via MVCC, but `SERIALIZABLE` is the guaranteed SQL standard protection.

---

## Task — Implement `SERIALIZABLE` Isolation

### Step 1 — Add the isolation level to your write methods

In `FilmDAOImpl.java`, find each method that uses `conn.setAutoCommit(false)` — that's `addFilm`, `updateFilm`, and `deleteFilm`. Directly after that line, add:

```java
conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
```

It must go **before** any statements are executed on the connection.

Example for `addFilm`:

```java
try (Connection conn = DBUtil.getConnection()) {
    conn.setAutoCommit(false);
    conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE); // ADD THIS

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        // ... set parameters ...
        ps.executeUpdate();
        conn.commit();
    } catch (SQLException e) {
        conn.rollback();
        e.printStackTrace();
    }
}
```

Apply the same line to `updateFilm` and `deleteFilm`.

---

### Step 2 — Add it to a read method

Pick one read method (e.g. `findByID`) and wrap it in an explicit transaction with `SERIALIZABLE`. This demonstrates protecting against non-repeatable and phantom reads.

```java
try (Connection conn = DBUtil.getConnection()) {
    conn.setAutoCommit(false);
    conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE); // ADD THIS

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        // ... execute query, map results ...
        conn.commit(); // commit even for reads — releases the locks
        return film;
    } catch (SQLException e) {
        conn.rollback();
        e.printStackTrace();
        return null;
    }
}
```

> Committing after a read-only serializable transaction is harmless — it just releases any range locks that were held.

---

### Step 3 — (Optional) Try a lower isolation level and observe the difference

To understand what `SERIALIZABLE` is actually protecting you from, try temporarily setting:

```java
conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
```

Then think through: which of the 4 problems above are you now exposed to? Which ones are still safe?

---

## Notes

- `setTransactionIsolation()` must be called **after** `setAutoCommit(false)` and **before** any SQL is executed.
- Each method in this project opens its own connection via `DBUtil.getConnection()`, so the isolation level is set fresh every call — this is fine.
- `SERIALIZABLE` is the strictest level and uses range locking, which can cause more lock contention. For this single-user CLI project that's irrelevant, but worth knowing for real-world use.
- In several methods, `PreparedStatement` is declared outside the try-with-resources block — consider moving it inside (`try (PreparedStatement ps = ...)`) so it closes automatically.
