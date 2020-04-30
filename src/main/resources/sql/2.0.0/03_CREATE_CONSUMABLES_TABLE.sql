-- Creating Consumables table
CREATE TABLE IF NOT EXISTS Consumables (
    id             INTEGER UNIQUE PRIMARY KEY,
    interventionId INTEGER NOT NULL,
    denomination   TEXT    NOT NULL,
    quantity       TEXT,
    FOREIGN KEY (interventionId)
        REFERENCES Interventions (id)
);
