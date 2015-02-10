#drop database med;
create database med;
use med;

Create table users(
    userId int auto_increment,
    nickname varchar(32) not null unique,
    passw varchar(32) not null,
    primary key(userId)
)engine=InnoDB;

create table pacjent(
	idPacjenta int not null auto_increment ,
	userId int default 0,
	imie varchar(64),
	nazwisko varchar(64),
	pesel int(11),
	telefon varchar(12),
	eMail varchar(64),
	adres varchar(64),
	plec boolean,
	primary key(idPacjenta),
	unique key(pesel),
	unique key(email),
	unique key(telefon),
    index(userId),
    foreign key(userId) references users(userId)
)engine = InnoDB;

create table lekarz(
	lekarzId int auto_increment ,
    specjalizacja text,	
    imie varchar(32),
    nazwisko varchar(32),
    primary key(lekarzId),
    index(lekarzId)
)engine = InnoDB;
#drop table badanie;
create table badanie(
	badanieId int not null auto_increment,
	userId int,
	dataBadania datetime,
	lekarzId int, 
    nazwabadania text,
	sala int,
    accepted int,
	primary key(badanieId),
    index(userId),
    index(lekarzId),
    foreign key(userId) references users(userId),
	foreign key(lekarzId) references lekarz(lekarzId)
)engine = InnoDB;
Select Now();
insert into med.lekarz(imie,nazwisko,specjalizacja) values('adam','rapista','pediatra');
insert into med.lekarz(imie,nazwisko,specjalizacja) values('Tomasz','Comasz','Internista');
insert into med.lekarz(imie,nazwisko,specjalizacja) values('Janina','Danina','Internista');
insert into med.lekarz(imie,nazwisko,specjalizacja) values('Omasz','Grzebaluch','Proktrolog');
insert into med.lekarz(imie,nazwisko,specjalizacja) values('Jan','Paczy','Okulista');
insert into med.badanie(dataBadania,lekarzId,nazwaBadania, sala, accepted) values('2014-08-10 15:00:00',1,'badanie pediatryczne',101,0);
insert into med.badanie(dataBadania,lekarzId,nazwaBadania, sala, accepted) values('2014-08-10 15:30:00',1,'badanie pediatrycne',101,0);
insert into med.badanie(dataBadania,lekarzId,nazwaBadania, sala, accepted) values('2014-08-10 16:00:00',1,'badanie pediatryczne',101,0);
insert into med.badanie(dataBadania,lekarzId,nazwaBadania, sala, accepted) values('2014-08-10 16:30:00',1,'badanie pediatryczne',101,0);
insert into med.badanie(dataBadania,lekarzId,nazwaBadania, sala, accepted) values('2014-08-11 16:30:00',1,'badanie pediatryczne',101,0);
insert into med.badanie(dataBadania,lekarzId,nazwaBadania, sala, accepted) values('2014-08-10 15:00:00',3,'badanie internistyczne',102,0);
insert into med.badanie(dataBadania,lekarzId,nazwaBadania, sala, accepted) values('2014-08-10 15:30:00',3,'badanie internistyczne',102,0);
insert into med.badanie(dataBadania,lekarzId,nazwaBadania, sala, accepted) values('2014-08-10 16:00:00',3,'badanie internistyczne',102,0);
insert into med.badanie(dataBadania,lekarzId,nazwaBadania, sala, accepted) values('2014-08-10 16:30:00',3,'badanie internistyczne',102,0);
insert into med.badanie(dataBadania,lekarzId,nazwaBadania, sala, accepted) values('2014-08-11 16:30:00',3,'badanie internistyczne',102,0);
insert into med.badanie(dataBadania,lekarzId,nazwaBadania, sala, accepted) values('2014-08-10 15:00:00',2,'badanie internistyczne',103,0);
insert into med.badanie(dataBadania,lekarzId,nazwaBadania, sala, accepted) values('2014-08-10 15:30:00',2,'badanie internistyczne',103,0);
insert into med.badanie(dataBadania,lekarzId,nazwaBadania, sala, accepted) values('2014-08-10 16:00:00',2,'badanie internistyczne',103,0);
insert into med.badanie(dataBadania,lekarzId,nazwaBadania, sala, accepted) values('2014-08-10 16:30:00',2,'badanie internistyczne',103,0);
insert into med.badanie(dataBadania,lekarzId,nazwaBadania, sala, accepted) values('2014-08-11 16:30:00',2,'badanie internistyczne',103,0);
insert  into med.users(nickname, passw) values('a','b');
INSERT INTO med.pacjent(imie, nazwisko, pesel, telefon, email, adres, plec, userId) VALUES('$imie','$nazwisko',12345678901,'$telefo','$emal','$adres','$gender',1);