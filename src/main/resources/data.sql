INSERT INTO authority (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO user (id, username, password, firstname, lastname, address, city, email, telephone)
  VALUES (1,'username10','','Elvis','Presley','Boschdijk 11','Eindhoven','elvis@hotmail.com','0273628749');

INSERT INTO user (id, username, password, firstname, lastname, address, city, email, telephone)
  VALUES (2,'username20','','John','Presley','Boschdijk 12','Eindhoven','el@hotmail.com','0273628749');

INSERT INTO user (id, username, password, firstname, lastname, address, city, email, telephone)
  VALUES (3,'username30','','Rob','Presley','Boschdijk 100','Eindhoven','elvisrob@hotmail.com','0273628749');

INSERT INTO user (id, username, password, firstname, lastname, address, city, email, telephone)
  VALUES (4,'username40','','Alf','Presley','Boschdijk 34','Paris','evisalf@hotmail.com','0273628749');

INSERT INTO user (id, username, password, firstname, lastname, address, city, email, telephone)
  VALUES (5,'username50','','Jimmy','Presley','Boschdijk 22','Eindhoven','elvisjimmy@hotmail.com','0273628749');

INSERT INTO user (id, username, password, firstname, lastname, address, city, email, telephone)
  VALUES (6,'username60','','Ray','Presley','Boschdijk 01','Eindhoven','elvisray@hotmail.com','0273628749');

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (3, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (4, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (5, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (6, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (1, 2);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2);
INSERT INTO user_authority (user_id, authority_id) VALUES (3, 2);

INSERT INTO vehicle (registration,colour,mileage,model,make,type,price,longitude,latitude,user_id)
  VALUES('SYEB233','red',23424,'X5','BMW','SUV',200,51.441643,5.469722,1);

INSERT INTO vehicle (registration,colour,mileage,model,make,type,price,longitude,latitude,user_id)
  VALUES('S34E33','black',0,'RX8','MAZDA','COUPE',100,51.443343,5.169722,2);

INSERT INTO vehicle (registration,colour,mileage,model,make,type,price,longitude,latitude,user_id)
  VALUES('SSUHFD3','red',50000,'S2','MERCEDES','SUV',150,48.856613,2.352222,2);

INSERT INTO vehicle (registration,colour,mileage,model,make,type,price,longitude,latitude,user_id)
  VALUES('UHSFDF3','blue',60000,'E-TYPE','JAGUAR','SPORT',400,51.507351,-0.127758,3);

INSERT INTO vehicle (registration,colour,mileage,model,make,type,price,longitude,latitude,user_id)
  VALUES('HDUSDD','white',20000,'AURIS','TOYOTA','HATCHBACK',50,52.370216,4.895168,4);

