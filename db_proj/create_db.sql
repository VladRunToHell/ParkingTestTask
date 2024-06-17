--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3 (Debian 16.3-1.pgdg120+1)
-- Dumped by pg_dump version 16.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- PostgreSQL database dump complete
--

SET default_tablespace = '';

SET default_table_access_method = heap;

CREATE TABLE public.machine (
    id_machine integer NOT NULL,
    borrow real,
    size integer NOT NULL,
    is_parked boolean NOT NULL
);


ALTER TABLE public.machine OWNER TO postgres;

CREATE SEQUENCE public.machine_id_machine_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.machine_id_machine_seq OWNER TO postgres;

ALTER SEQUENCE public.machine_id_machine_seq OWNED BY public.machine.id_machine;

CREATE TABLE public.park_area (
    id_park_area integer NOT NULL,
    place_quan integer NOT NULL,
    free_place_quan integer NOT NULL,
    tax real NOT NULL
);


ALTER TABLE public.park_area OWNER TO postgres;

CREATE SEQUENCE public.park_area_id_park_area_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.park_area_id_park_area_seq OWNER TO postgres;


ALTER SEQUENCE public.park_area_id_park_area_seq OWNED BY public.park_area.id_park_area;

CREATE TABLE public.parking (
    id_machine integer NOT NULL,
    id_park_area integer NOT NULL,
    start_time timestamp without time zone NOT NULL
);


ALTER TABLE public.parking OWNER TO postgres;

ALTER TABLE ONLY public.machine ALTER COLUMN id_machine SET DEFAULT nextval('public.machine_id_machine_seq'::regclass);

ALTER TABLE ONLY public.park_area ALTER COLUMN id_park_area SET DEFAULT nextval('public.park_area_id_park_area_seq'::regclass);

ALTER TABLE ONLY public.machine
    ADD CONSTRAINT machine_pkey PRIMARY KEY (id_machine);

ALTER TABLE ONLY public.park_area
    ADD CONSTRAINT park_area_pkey PRIMARY KEY (id_park_area);

ALTER TABLE ONLY public.parking
    ADD CONSTRAINT parking_pkey PRIMARY KEY (id_machine, id_park_area);

ALTER TABLE ONLY public.parking
    ADD CONSTRAINT fk_machine FOREIGN KEY (id_machine) REFERENCES public.machine(id_machine);

ALTER TABLE ONLY public.parking
    ADD CONSTRAINT fk_park_area FOREIGN KEY (id_park_area) REFERENCES public.park_area(id_park_area);

INSERT INTO public.machine(borrow, size, is_parked) values (0.0, 2, '0');
INSERT INTO public.machine(borrow, size, is_parked) values (0.0, 1, '0');
INSERT INTO public.machine(borrow, size, is_parked) values (10.0, 2, '0');
INSERT INTO public.park_area(place_quan, free_place_quan, tax) values (3, 3, 10.0);

