-- add role
INSERT INTO public.tb_roles (active,deleted,created_at,updated_at,"version",uuid,description,name,pretty_name) VALUES
   (true,false,current_timestamp,current_timestamp,0,'60febc0c-f421-4b52-b540-4f7f797ddbe1','Owner access.','OWNER','Owner'),
   (true,false,current_timestamp,current_timestamp,0,'5dcb5cf5-578d-4452-8dac-92676420070f','Administrator access.','ADMIN','Admin'),
   (true,false,current_timestamp,current_timestamp,0,'1e7e535c-a48b-4189-beef-3857d74df6df','Api access.','API','Api');
;
