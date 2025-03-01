--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4
-- Dumped by pg_dump version 17.4

-- Started on 2025-03-01 22:00:51

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 221 (class 1259 OID 17364)
-- Name: _article; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public._article (
    article_id integer NOT NULL,
    discount double precision NOT NULL,
    title text,
    description text,
    author text,
    media text,
    is_active boolean,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public._article OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 17363)
-- Name: _article_article_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public._article_article_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public._article_article_id_seq OWNER TO postgres;

--
-- TOC entry 4953 (class 0 OID 0)
-- Dependencies: 220
-- Name: _article_article_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public._article_article_id_seq OWNED BY public._article.article_id;


--
-- TOC entry 217 (class 1259 OID 17209)
-- Name: _users_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public._users_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public._users_seq OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 18805)
-- Name: booking; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.booking (
    booking_number integer NOT NULL,
    booking_date timestamp without time zone NOT NULL,
    pickup_location character varying(255) NOT NULL,
    drop_off_location character varying(255) NOT NULL,
    car_number character varying(50) NOT NULL,
    taxes numeric(10,2) NOT NULL,
    distance double precision NOT NULL,
    estimatedtime double precision NOT NULL,
    tax_without_cost double precision NOT NULL,
    total_amount numeric(10,2) NOT NULL,
    customer_registration_number character varying(50) NOT NULL,
    driver_id character varying(50) NOT NULL,
    status character varying(50) NOT NULL,
    created_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.booking OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 18804)
-- Name: booking_booking_number_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.booking_booking_number_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.booking_booking_number_seq OWNER TO postgres;

--
-- TOC entry 4954 (class 0 OID 0)
-- Dependencies: 226
-- Name: booking_booking_number_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.booking_booking_number_seq OWNED BY public.booking.booking_number;


--
-- TOC entry 223 (class 1259 OID 18241)
-- Name: customer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customer (
    registration_number integer NOT NULL,
    root_user_id integer,
    address character varying(255),
    nic character varying(20),
    phone_number character varying(15) NOT NULL
);


ALTER TABLE public.customer OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 18240)
-- Name: customer_registration_number_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.customer_registration_number_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.customer_registration_number_seq OWNER TO postgres;

--
-- TOC entry 4955 (class 0 OID 0)
-- Dependencies: 222
-- Name: customer_registration_number_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.customer_registration_number_seq OWNED BY public.customer.registration_number;


--
-- TOC entry 235 (class 1259 OID 19493)
-- Name: driver; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.driver (
    driver_registration_number integer NOT NULL,
    root_user_id integer,
    driver_profile_picture text,
    driver_nic character varying(50) NOT NULL,
    phone_number character varying(20) NOT NULL,
    license_number character varying(50) NOT NULL,
    license_expiry_date date,
    driver_address text,
    vehicle_assigned character varying(10) DEFAULT 'FALSE'::character varying NOT NULL,
    driver_status character varying(20) DEFAULT 'Active'::character varying NOT NULL,
    emergency_contact character varying(20),
    date_of_birth date NOT NULL,
    date_of_joining date,
    license_image_front text,
    license_image_back text
);


ALTER TABLE public.driver OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 19492)
-- Name: driver_driver_registration_number_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.driver_driver_registration_number_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.driver_driver_registration_number_seq OWNER TO postgres;

--
-- TOC entry 4956 (class 0 OID 0)
-- Dependencies: 234
-- Name: driver_driver_registration_number_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.driver_driver_registration_number_seq OWNED BY public.driver.driver_registration_number;


--
-- TOC entry 219 (class 1259 OID 17333)
-- Name: guideline; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.guideline (
    guidance_id integer NOT NULL,
    title character varying(255) NOT NULL,
    description text NOT NULL,
    category character varying(100) NOT NULL,
    priority character varying(50) NOT NULL,
    related_to character varying(255)
);


ALTER TABLE public.guideline OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 17332)
-- Name: guideline_guidance_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.guideline_guidance_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.guideline_guidance_id_seq OWNER TO postgres;

--
-- TOC entry 4957 (class 0 OID 0)
-- Dependencies: 218
-- Name: guideline_guidance_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.guideline_guidance_id_seq OWNED BY public.guideline.guidance_id;


--
-- TOC entry 225 (class 1259 OID 18248)
-- Name: manager; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.manager (
    registration_number integer NOT NULL,
    root_user_id integer,
    address character varying(255),
    nic character varying(50),
    phone_number character varying(50) NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);


ALTER TABLE public.manager OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 18247)
-- Name: manager_registration_number_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.manager_registration_number_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.manager_registration_number_seq OWNER TO postgres;

--
-- TOC entry 4958 (class 0 OID 0)
-- Dependencies: 224
-- Name: manager_registration_number_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.manager_registration_number_seq OWNED BY public.manager.registration_number;


--
-- TOC entry 231 (class 1259 OID 19270)
-- Name: token; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.token (
    id integer NOT NULL,
    token text NOT NULL,
    token_type character varying(50) DEFAULT 'BEARER'::character varying NOT NULL,
    revoked boolean DEFAULT false NOT NULL,
    expired boolean DEFAULT false NOT NULL,
    user_id integer
);


ALTER TABLE public.token OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 19269)
-- Name: token_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.token_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.token_id_seq OWNER TO postgres;

--
-- TOC entry 4959 (class 0 OID 0)
-- Dependencies: 230
-- Name: token_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.token_id_seq OWNED BY public.token.id;


--
-- TOC entry 229 (class 1259 OID 19061)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    role character varying(50) NOT NULL,
    user_profile_pic text,
    CONSTRAINT users_role_check CHECK (((role)::text = ANY ((ARRAY['ADMIN'::character varying, 'DRIVER'::character varying, 'CUSTOMER'::character varying])::text[])))
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 19060)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 4960 (class 0 OID 0)
-- Dependencies: 228
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 233 (class 1259 OID 19409)
-- Name: vehicle; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vehicle (
    id integer NOT NULL,
    registration_number text NOT NULL,
    vehicle_image text NOT NULL,
    make text NOT NULL,
    model text NOT NULL,
    year_of_manufacture integer NOT NULL,
    color text,
    fuel_type text,
    engine_capacity text,
    chassis_number text NOT NULL,
    vehicle_type text NOT NULL,
    owner_name text NOT NULL,
    owner_contact text NOT NULL,
    owner_address text,
    insurance_provider text,
    insurance_policy_number text,
    insurance_expiry_date date,
    seating_capacity integer NOT NULL,
    license_plate_number text NOT NULL,
    permit_type text,
    air_conditioning boolean,
    additional_features text
);


ALTER TABLE public.vehicle OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 19408)
-- Name: vehicle_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.vehicle_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.vehicle_id_seq OWNER TO postgres;

--
-- TOC entry 4961 (class 0 OID 0)
-- Dependencies: 232
-- Name: vehicle_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.vehicle_id_seq OWNED BY public.vehicle.id;


--
-- TOC entry 4737 (class 2604 OID 17367)
-- Name: _article article_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public._article ALTER COLUMN article_id SET DEFAULT nextval('public._article_article_id_seq'::regclass);


--
-- TOC entry 4742 (class 2604 OID 18808)
-- Name: booking booking_number; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.booking ALTER COLUMN booking_number SET DEFAULT nextval('public.booking_booking_number_seq'::regclass);


--
-- TOC entry 4740 (class 2604 OID 18244)
-- Name: customer registration_number; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer ALTER COLUMN reg