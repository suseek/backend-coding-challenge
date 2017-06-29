CREATE TABLE expenses (
  id             SERIAL         NOT NULL PRIMARY KEY,
  date           DATE           NOT NULL,
  amount         NUMERIC(14, 2) NOT NULL,
  initial_amount NUMERIC(14, 2) NOT NULL,
  vat_rate       NUMERIC(14, 2) NOT NULL,
  calculated_vat NUMERIC(14, 2) NOT NULL,
  currency_code  VARCHAR(3)     NOT NULL,
  exchange_rate  NUMERIC(10, 5) NULL,
  reason         VARCHAR(1000)  NULL,
  created_at     TIMESTAMP      NULL
);

CREATE INDEX expenses_date_index
  ON expenses (date);