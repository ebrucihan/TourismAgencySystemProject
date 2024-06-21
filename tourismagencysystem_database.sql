PGDMP  $                     |           tourismagencysystem    15.7    16.3 &    *           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            +           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            ,           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            -           1262    16399    tourismagencysystem    DATABASE     �   CREATE DATABASE tourismagencysystem WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Turkish_T�rkiye.1254';
 #   DROP DATABASE tourismagencysystem;
                postgres    false            �            1259    16423    facility    TABLE     q  CREATE TABLE public.facility (
    facility_id bigint NOT NULL,
    facility_hotel_id bigint NOT NULL,
    facility_free_park boolean NOT NULL,
    facility_free_wifi boolean NOT NULL,
    facility_pool boolean NOT NULL,
    facility_concierge boolean NOT NULL,
    facility_spa boolean NOT NULL,
    facility_room_service boolean NOT NULL,
    facility_gym boolean
);
    DROP TABLE public.facility;
       public         heap    postgres    false            �            1259    16422    facility_facility_id_seq    SEQUENCE     �   ALTER TABLE public.facility ALTER COLUMN facility_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.facility_facility_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    221            �            1259    16409    hotel    TABLE     w  CREATE TABLE public.hotel (
    hotel_id bigint NOT NULL,
    hotel_name text NOT NULL,
    hotel_adress text NOT NULL,
    hotel_city text NOT NULL,
    hotel_region text NOT NULL,
    hotel_mail text NOT NULL,
    hotel_mpno text NOT NULL,
    hotel_stars character varying(7) NOT NULL,
    hotel_facility_id integer NOT NULL,
    hotel_pension_type_id integer NOT NULL
);
    DROP TABLE public.hotel;
       public         heap    postgres    false            �            1259    16408    hotel_hotel_id_seq    SEQUENCE     �   ALTER TABLE public.hotel ALTER COLUMN hotel_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.hotel_hotel_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    217            �            1259    16417    pensiontype    TABLE     �  CREATE TABLE public.pensiontype (
    pension_type_id bigint NOT NULL,
    pension_hotel_id bigint NOT NULL,
    pension_type_ultra boolean NOT NULL,
    pension_type_hsd boolean NOT NULL,
    pension_type_breakfast boolean NOT NULL,
    pension_type_tam boolean NOT NULL,
    pension_type_yarim boolean NOT NULL,
    pension_type_just_bed boolean NOT NULL,
    pension_type_ahfc boolean NOT NULL
);
    DROP TABLE public.pensiontype;
       public         heap    postgres    false            �            1259    16416    pensiontype_pension_type_id_seq    SEQUENCE     �   ALTER TABLE public.pensiontype ALTER COLUMN pension_type_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.pensiontype_pension_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    219            �            1259    24591    reservation    TABLE     �  CREATE TABLE public.reservation (
    reservation_id bigint NOT NULL,
    reservation_room_id bigint NOT NULL,
    reservation_customer_name character varying(100) NOT NULL,
    reservation_customer_contact character varying(100) NOT NULL,
    reservation_check_in_date date NOT NULL,
    reservation_check_out_date date NOT NULL,
    reservation_total_price numeric(10,2) NOT NULL,
    reservation_guest_count_adult integer NOT NULL,
    reservation_guest_count_child integer NOT NULL,
    reservation_customer_email character varying(50) NOT NULL,
    reservation_customer_tc character varying(50) NOT NULL,
    reservation_customer_note character varying NOT NULL
);
    DROP TABLE public.reservation;
       public         heap    postgres    false            �            1259    24590    reservation_reservation_id_seq    SEQUENCE     �   ALTER TABLE public.reservation ALTER COLUMN reservation_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.reservation_reservation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    225            �            1259    16429    room    TABLE     b  CREATE TABLE public.room (
    room_id bigint NOT NULL,
    room_hotel_id bigint NOT NULL,
    room_type character varying(50) NOT NULL,
    price_adult numeric(10,2) NOT NULL,
    price_child numeric(10,2) NOT NULL,
    room_stock integer NOT NULL,
    room_bed_count integer NOT NULL,
    room_square_meters integer NOT NULL,
    room_tv boolean NOT NULL,
    room_minibar boolean NOT NULL,
    room_gameconsole boolean NOT NULL,
    room_safe boolean NOT NULL,
    room_projector boolean NOT NULL,
    room_season_type character varying(50) NOT NULL,
    room_pension_type character varying(50) NOT NULL
);
    DROP TABLE public.room;
       public         heap    postgres    false            �            1259    16428    room_room_id_seq    SEQUENCE     �   ALTER TABLE public.room ALTER COLUMN room_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.room_room_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    223            �            1259    16401    user    TABLE     �   CREATE TABLE public."user" (
    user_id bigint NOT NULL,
    user_name text NOT NULL,
    user_pass text NOT NULL,
    user_role text NOT NULL
);
    DROP TABLE public."user";
       public         heap    postgres    false            �            1259    16400    user_user_id_seq    SEQUENCE     �   ALTER TABLE public."user" ALTER COLUMN user_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.user_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    215            #          0    16423    facility 
   TABLE DATA           �   COPY public.facility (facility_id, facility_hotel_id, facility_free_park, facility_free_wifi, facility_pool, facility_concierge, facility_spa, facility_room_service, facility_gym) FROM stdin;
    public          postgres    false    221   K5                 0    16409    hotel 
   TABLE DATA           �   COPY public.hotel (hotel_id, hotel_name, hotel_adress, hotel_city, hotel_region, hotel_mail, hotel_mpno, hotel_stars, hotel_facility_id, hotel_pension_type_id) FROM stdin;
    public          postgres    false    217   �5       !          0    16417    pensiontype 
   TABLE DATA           �   COPY public.pensiontype (pension_type_id, pension_hotel_id, pension_type_ultra, pension_type_hsd, pension_type_breakfast, pension_type_tam, pension_type_yarim, pension_type_just_bed, pension_type_ahfc) FROM stdin;
    public          postgres    false    219   �6       '          0    24591    reservation 
   TABLE DATA           a  COPY public.reservation (reservation_id, reservation_room_id, reservation_customer_name, reservation_customer_contact, reservation_check_in_date, reservation_check_out_date, reservation_total_price, reservation_guest_count_adult, reservation_guest_count_child, reservation_customer_email, reservation_customer_tc, reservation_customer_note) FROM stdin;
    public          postgres    false    225   7       %          0    16429    room 
   TABLE DATA           �   COPY public.room (room_id, room_hotel_id, room_type, price_adult, price_child, room_stock, room_bed_count, room_square_meters, room_tv, room_minibar, room_gameconsole, room_safe, room_projector, room_season_type, room_pension_type) FROM stdin;
    public          postgres    false    223   �7                 0    16401    user 
   TABLE DATA           J   COPY public."user" (user_id, user_name, user_pass, user_role) FROM stdin;
    public          postgres    false    215   8       .           0    0    facility_facility_id_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.facility_facility_id_seq', 51, true);
          public          postgres    false    220            /           0    0    hotel_hotel_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.hotel_hotel_id_seq', 7, true);
          public          postgres    false    216            0           0    0    pensiontype_pension_type_id_seq    SEQUENCE SET     N   SELECT pg_catalog.setval('public.pensiontype_pension_type_id_seq', 59, true);
          public          postgres    false    218            1           0    0    reservation_reservation_id_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public.reservation_reservation_id_seq', 22, true);
          public          postgres    false    224            2           0    0    room_room_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.room_room_id_seq', 8, true);
          public          postgres    false    222            3           0    0    user_user_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.user_user_id_seq', 2, true);
          public          postgres    false    214            �           2606    16427    facility facility_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.facility
    ADD CONSTRAINT facility_pkey PRIMARY KEY (facility_id);
 @   ALTER TABLE ONLY public.facility DROP CONSTRAINT facility_pkey;
       public            postgres    false    221            �           2606    16415    hotel hotel_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.hotel
    ADD CONSTRAINT hotel_pkey PRIMARY KEY (hotel_id);
 :   ALTER TABLE ONLY public.hotel DROP CONSTRAINT hotel_pkey;
       public            postgres    false    217            �           2606    16421    pensiontype pensiontype_pkey 
   CONSTRAINT     g   ALTER TABLE ONLY public.pensiontype
    ADD CONSTRAINT pensiontype_pkey PRIMARY KEY (pension_type_id);
 F   ALTER TABLE ONLY public.pensiontype DROP CONSTRAINT pensiontype_pkey;
       public            postgres    false    219            �           2606    24595    reservation reservation_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_pkey PRIMARY KEY (reservation_id);
 F   ALTER TABLE ONLY public.reservation DROP CONSTRAINT reservation_pkey;
       public            postgres    false    225            �           2606    16433    room room_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY public.room
    ADD CONSTRAINT room_pkey PRIMARY KEY (room_id);
 8   ALTER TABLE ONLY public.room DROP CONSTRAINT room_pkey;
       public            postgres    false    223                       2606    16407    user user_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (user_id);
 :   ALTER TABLE ONLY public."user" DROP CONSTRAINT user_pkey;
       public            postgres    false    215            �           2606    16440 "   hotel hotel_hotel_facility_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.hotel
    ADD CONSTRAINT hotel_hotel_facility_id_fkey FOREIGN KEY (hotel_facility_id) REFERENCES public.facility(facility_id) NOT VALID;
 L   ALTER TABLE ONLY public.hotel DROP CONSTRAINT hotel_hotel_facility_id_fkey;
       public          postgres    false    3205    221    217            �           2606    16445 &   hotel hotel_hotel_pension_type_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.hotel
    ADD CONSTRAINT hotel_hotel_pension_type_id_fkey FOREIGN KEY (hotel_pension_type_id) REFERENCES public.pensiontype(pension_type_id) NOT VALID;
 P   ALTER TABLE ONLY public.hotel DROP CONSTRAINT hotel_hotel_pension_type_id_fkey;
       public          postgres    false    219    3203    217            �           2606    24596 0   reservation reservation_reservation_room_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_reservation_room_id_fkey FOREIGN KEY (reservation_room_id) REFERENCES public.room(room_id);
 Z   ALTER TABLE ONLY public.reservation DROP CONSTRAINT reservation_reservation_room_id_fkey;
       public          postgres    false    3207    225    223            �           2606    16450    room room_room_hotel_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.room
    ADD CONSTRAINT room_room_hotel_id_fkey FOREIGN KEY (room_hotel_id) REFERENCES public.hotel(hotel_id) NOT VALID;
 F   ALTER TABLE ONLY public.room DROP CONSTRAINT room_room_hotel_id_fkey;
       public          postgres    false    223    217    3201            #   ]   x�e�K�0�5Ɣj��'��Q��I�x�dZ3=^�T~� ��#� #�6 +H�T�	[�Y���	{ل4�Ǽ8��.���3�O�����7�L         �   x�=��N�0���S�sTE.�k���1��`��8	�$^��;�)���n��6�!N� ��6�\,[䟪�PUh��F($��2a9���;����~9�0�<�QeSAK�[a�z�� ��]�%�����jZ>z����Q^ه�r�3�]�"�1R��/�/��������{�9��i�!���4J�+����[q�����Go      !   g   x�e�K
�0E�q�iڦ��8�
�?*UQ/28����;�B�I��A*�A:d@4�� �R"e�,>�.��:i�,���H�!�1�2#UR�-��q}��Ⱥ���j�      '   �   x���=�0��Sp*��Cِ�3K��A�BT	�m�n���=��{�]���\KF��GMu3���=,U�%XĜ��p�u|'q��VYQ$����T[�U�1$�rK��d0���	2��b0u��gwL�l��]�f�=���a�>L�m�\ɥK�j��5n��O/o�#�!��!��2����N-      %   �   x�m�M
� ���0A�_��H3%j�H	���?5i	�at���F�@�1h���v
Yc�5�Z�1�#�W��>=�N�4�
%:�!�ՙiw&�ɤ����e7����������H�w`�7�<�k�&o󌖜�@�$%�ܘ��5ɧ`�}�t?�         1   x�3�LL����4426�tt����2�,�/�N-����y�q��qqq s
�     