PRAGMA synchronous = OFF;
PRAGMA journal_mode = MEMORY;
BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `stockquotedata_basic` (
  `stockquotedatabasic_id` integer NOT NULL PRIMARY KEY AUTOINCREMENT
,  `isin` CHAR(12) NOT NULL
,  `stockname` VARCHAR(100) NULL
,  `stockindex` VARCHAR(10) NULL
,  `symbol` VARCHAR(7) NULL
,  `financial_year` DATE NULL
CREATE TABLE IF NOT EXISTS `stockquotedata` (
  `stockquotedata_id` integer NOT NULL PRIMARY KEY AUTOINCREMENT
,  `stockquotedatabasic_id` integer NOT NULL
,  `fetch_date` DATE NOT NULL
,  `return_on_equity` DECIMAL(5,2) NULL
,  `ebit_margin` DECIMAL(5,2) NULL
,  `equity_ration` DECIMAL(5,2) NULL
,  `analysts_count` integer  NULL
,  `analysts_opinion` integer  NULL
,  INDEX `stockquotedatabasic_fk_idx` (`stockquotedatabasic_id` ASC)
,  CONSTRAINT `stockquotedatabasic_fk`
,    FOREIGN KEY (`stockquotedatabasic_id`)
,    REFERENCES `stockquotedata_basic` (`stockquotedatabasic_id`)
,    ON DELETE NO ACTION
CREATE TABLE IF NOT EXISTS `historical_quotes` (
  `historical_quotes_id` integer NOT NULL PRIMARY KEY AUTOINCREMENT
,  `stockquotedata_id` integer NOT NULL
,  `quote_date` DATE NOT NULL
,  `quote` DECIMAL(5,2) NULL
,  INDEX `hq_stockquotedata_fk_idx` (`stockquotedata_id` ASC)
,  CONSTRAINT `hq_stockquotedata_fk`
,    FOREIGN KEY (`stockquotedata_id`)
,    REFERENCES `stockquotedata` (`stockquotedata_id`)
,    ON DELETE NO ACTION
CREATE TABLE IF NOT EXISTS `price_earnings_ratio` (
  `price_earnings_ratio_id` integer NOT NULL PRIMARY KEY AUTOINCREMENT
,  `stockquotedata_id` integer NOT NULL
,  `per_year` YEAR NULL
,  `per_value` DECIMAL(5,2) NULL
,  `estimated` integer NULL
,  INDEX `per_stockquotedata_fk_idx` (`stockquotedata_id` ASC)
,  CONSTRAINT `per_stockquotedata_fk`
,    FOREIGN KEY (`stockquotedata_id`)
,    REFERENCES `stockquotedata` (`stockquotedata_id`)
,    ON DELETE NO ACTION
CREATE TABLE IF NOT EXISTS `eps` (
  `eps_id` integer NOT NULL PRIMARY KEY AUTOINCREMENT
,  `stockquotedata_id` integer NOT NULL
,  `per_year` YEAR NULL
,  `per_value` DECIMAL(5,2) NULL
,  `estimated` integer NULL
,  INDEX `eps_stockquotedata_fk_idx` (`stockquotedata_id` ASC)
,  CONSTRAINT `eps_stockquotedata_fk`
,    FOREIGN KEY (`stockquotedata_id`)
,    REFERENCES `stockquotedata` (`stockquotedata_id`)
,    ON DELETE NO ACTION
END TRANSACTION;
