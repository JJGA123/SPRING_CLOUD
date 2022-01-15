CREATE TABLE TRANSACTION ( 
   ID_TRANSACTION INTEGER PRIMARY KEY AUTO_INCREMENT, 
   AMOUNT INTEGER NOT NULL,
   CURRENCY VARCHAR(3) NOT NULL, 
   NUMBER_ORIGIN VARCHAR(20) NOT NULL,
   NUMBER_DESTINATION VARCHAR(20) NOT NULL,
   DESCRIPTION VARCHAR(50),
   ID_USER INTEGER NOT NULL
);