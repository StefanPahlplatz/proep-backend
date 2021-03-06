INSERT INTO authority (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO user (id, username, password, firstname, lastname, address, city, email, telephone)
  VALUES (1,'username10','$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.','Elvis','Presley','Boschdijk 11','Eindhoven','elvis@hotmail.com','0273628749');

INSERT INTO user (id, username, password, firstname, lastname, address, city, email, telephone)
  VALUES (2,'username20','$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.','John','Presley','Boschdijk 12','Eindhoven','el@hotmail.com','0273628749');

INSERT INTO user (id, username, password, firstname, lastname, address, city, email, telephone)
  VALUES (3,'username30','$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.','Rob','Presley','Boschdijk 100','Eindhoven','elvisrob@hotmail.com','0273628749');

INSERT INTO user (id, username, password, firstname, lastname, address, city, email, telephone)
  VALUES (4,'username40','$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.','Alf','Presley','Boschdijk 34','Paris','evisalf@hotmail.com','0273628749');

INSERT INTO user (id, username, password, firstname, lastname, address, city, email, telephone)
  VALUES (5,'username50','$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.','Jimmy','Presley','Boschdijk 22','Eindhoven','elvisjimmy@hotmail.com','0273628749');

INSERT INTO user (id, username, password, firstname, lastname, address, city, email, telephone)
  VALUES (6,'username60','$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.','Ray','Presley','Boschdijk 01','Eindhoven','elvisray@hotmail.com','0273628749');

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (3, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (4, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (5, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (6, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (1, 2);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2);
INSERT INTO user_authority (user_id, authority_id) VALUES (3, 2);

INSERT INTO vehicle (id,registration,colour,mileage,model,make,type,price,longitude,latitude,user_id)
  VALUES(1,'SYEB233','red',23424,'X5','BMW','SUV',200,51.441643,5.469722,1);

INSERT INTO image (id,path,vehicle)
  VALUES(1, 'https://pictures.dealer.com/b/bmwofhonoluluhi/0435/191f0a42830392868afc89f738a8ac92x.jpg?impolicy=resize&w=650', 1);

INSERT INTO image (id,path,vehicle)
  VALUES(12, 'https://img2.carmax.com/img/vehicles/17402302/1/385.jpg', 1);

INSERT INTO image (id,path,vehicle)
  VALUES(13, 'https://www.coches.com/fotos_historicas/bmw/X5/high_d877029f582bd5b4b5ba51ce2530e338.jpg', 1);






INSERT INTO vehicle (id,registration,colour,mileage,model,make,type,price,longitude,latitude,user_id)
  VALUES(2,'S34E33','black',0,'RX8','MAZDA','COUPE',100,51.443343,5.169722,2);

INSERT INTO image (id,path,vehicle)
  VALUES(2, 'https://upload.wikimedia.org/wikipedia/commons/5/57/2006_Mazda_RX-8_2.6.jpg', 2);

INSERT INTO image (id,path,vehicle)
  VALUES(10, 'https://cdn.bringatrailer.com/wp-content/uploads/2017/09/1-7-940x705.jpg', 2);

INSERT INTO image (id,path,vehicle)
  VALUES(11, 'https://www.cstatic-images.com/stock/400x500/227755.png', 2);





INSERT INTO vehicle (id,registration,colour,mileage,model,make,type,price,longitude,latitude,user_id)
  VALUES(3,'SSUHFD3','red',50000,'S2','MERCEDES','SUV',150,48.856613,2.352222,2);

INSERT INTO image (id,path,vehicle)
  VALUES(3, 'https://www.s2-cc.com/wp-content/uploads/2016/11/dsc03512-1024x684.jpg', 3);

INSERT INTO image (id,path,vehicle)
  VALUES(14, 'https://di-uploads-pod4.dealerinspire.com/mercedesbenzbrampton/uploads/2016/04/2016-Mercedes-Benz-S550-s2.jpg', 3);

INSERT INTO image (id,path,vehicle)
  VALUES(15, 'https://di-uploads-pod4.s3.amazonaws.com/mercedesbenzbrampton/uploads/2016/03/2016-AMG%C2%AE-CLA45-Coupe-s2.jpg', 3);





INSERT INTO vehicle (id,registration,colour,mileage,model,make,type,price,longitude,latitude,user_id)
  VALUES(4,'UHSFDF3','blue',60000,'E-TYPE','JAGUAR','SPORT',400,51.507351,-0.127758,3);

INSERT INTO image (id,path,vehicle)
  VALUES(4, 'https://scd.france24.com/en/files/imagecache/rfi_16x9_1024_578/article/image/jaguar-e-type-electric-2.jpg', 4);

INSERT INTO image (id,path,vehicle)
  VALUES(16, 'https://img.newatlas.com/jaguar-e-type-reborn-1.jpg?auto=format%2Ccompress&ch=Width%2CDPR&fit=crop&h=347&q=60&rect=146%2C317%2C1211%2C681&w=616&s=0ee483be84ae4d3873852628abf91685', 4);

INSERT INTO image (id,path,vehicle)
  VALUES(17, 'https://images.abcdn.nl/image/3408485574_1dbdd4d0-1973-jaguar-e-type-series-3-22-2.jpg?rect=46%2C104%2C1556%2C875&w=880&s=b21154e63ab74ef2533c73d83a2d2690', 4);




INSERT INTO vehicle (id,registration,colour,mileage,model,make,type,price,longitude,latitude,user_id)
  VALUES(5,'HDUSDD','white',20000,'AURIS','TOYOTA','HATCHBACK',50,52.370216,4.895168,4);

INSERT INTO image (id,path,vehicle)
  VALUES(5, 'https://www.autocar.co.uk/sites/autocar.co.uk/files/styles/gallery_slide/public/toyota-auris-side-profile.jpg?itok=WTuCsY_y', 5);

INSERT INTO image (id,path,vehicle)
  VALUES(18, 'https://www.autocar.co.uk/sites/autocar.co.uk/files/styles/gallery_slide/public/toyota-auris.jpg?itok=ZNmN3FoT', 5);

INSERT INTO image (id,path,vehicle)
  VALUES(19, 'https://media.whatcar.com/450x299/migration/reviews/1ae87743c4e250d1268812116ce41323951f1f9c.jpg', 5);
