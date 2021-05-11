CREATE TABLE master_resource_types (
  id SERIAL PRIMARY KEY,
  name CHARACTER VARYING (50) NOT NULL UNIQUE,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE trigger master_resource_types_creation_timestamp_trigger
   BEFORE INSERT ON master_resource_types
   for each row EXECUTE procedure creation_timestamp_handler();
   
CREATE trigger master_resource_types_updation_timestamp_trigger
   BEFORE UPDATE ON master_resource_types
   for each row EXECUTE procedure updation_timestamp_handler();
   
   
CREATE TABLE users (
  id UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  email_id CHARACTER VARYING (50) NOT NULL UNIQUE,
  password CHARACTER VARYING (100) NOT NULL,
  first_name CHARACTER VARYING (50) NOT NULL,
  last_name CHARACTER VARYING (50) NOT NULL,
  contact_number CHARACTER VARYING (10) DEFAULT NULL,
  state_id INTEGER NOT NULL,
  latitude DECIMAL NOT NULL,
  longitude DECIMAL NOT NULL,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE trigger users_creation_timestamp_trigger
   BEFORE INSERT ON users
   for each row EXECUTE procedure creation_timestamp_handler();
   
CREATE trigger users_updation_timestamp_trigger
   BEFORE UPDATE ON users
   for each row EXECUTE procedure updation_timestamp_handler();
   
   
CREATE TABLE requests (
  id UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  resource_type_id INTEGER NOT NULL,
  requested_by_user_id UUID NOT NULL,
  title CHARACTER VARYING (100) NOT NULL, 
  description CHARACTER VARYING (1000) NOT NULL, 
  latitude DECIMAL NOT NULL,
  longitude DECIMAL NOT NULL,
  is_active BOOLEAN NOT NULL,
  fulfilled_by_user_id UUID DEFAULT NULL,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  CONSTRAINT request_fkey_requested_by_user FOREIGN KEY (requested_by_user_id)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
  CONSTRAINT request_fkey_fulfilled_by_user FOREIGN KEY (fulfilled_by_user_id)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
  CONSTRAINT request_fkey_resource_type FOREIGN KEY (resource_type_id)
        REFERENCES master_resource_types (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE trigger requests_creation_timestamp_trigger
   BEFORE INSERT ON requests
   for each row EXECUTE procedure creation_timestamp_handler();
   
CREATE trigger requests_updation_timestamp_trigger
   BEFORE UPDATE ON requests
   for each row EXECUTE procedure updation_timestamp_handler();
   
   
CREATE TABLE resources (
  id UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  resource_type_id INTEGER NOT NULL,
  user_id UUID NOT NULL,
  title CHARACTER VARYING (100) NOT NULL, 
  description CHARACTER VARYING (1000) NOT NULL,
  count INTEGER NOT NULL,
  latitude DECIMAL NOT NULL,
  longitude DECIMAL NOT NULL,
  is_active BOOLEAN NOT NULL,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  CONSTRAINT resource_fkey_user FOREIGN KEY (user_id)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
  CONSTRAINT resource_fkey_resource_type FOREIGN KEY (resource_type_id)
        REFERENCES master_resource_types (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);   

CREATE trigger resources_creation_timestamp_trigger
   BEFORE INSERT ON resources
   for each row EXECUTE procedure creation_timestamp_handler();
   
CREATE trigger resources_updation_timestamp_trigger
   BEFORE UPDATE ON resources
   for each row EXECUTE procedure updation_timestamp_handler();
   
   
CREATE TABLE follow_mappings (
  id SERIAL PRIMARY KEY,
  follower_user_id UUID NOT NULL,
  followed_user_id UUID NOT NULL,
  is_active BOOLEAN NOT NULL,
  UNIQUE (follower_user_id,followed_user_id),
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  CONSTRAINT follow_mappings_fkey_follower FOREIGN KEY (follower_user_id)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
  CONSTRAINT follow_mappings_fkey_followed FOREIGN KEY (followed_user_id)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);      

CREATE trigger follow_mappings_creation_timestamp_trigger
   BEFORE INSERT ON follow_mappings
   for each row EXECUTE procedure creation_timestamp_handler();
   
CREATE trigger follow_mappings_updation_timestamp_trigger
   BEFORE UPDATE ON follow_mappings
   for each row EXECUTE procedure updation_timestamp_handler();
   
   
CREATE TABLE report_mappings (
  id SERIAL PRIMARY KEY,
  user_id UUID NOT NULL,
  item_type CHARACTER VARYING (20) NOT NULL,
  item_id UUID NOT NULL,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  UNIQUE (user_id, item_id),
  CONSTRAINT report_mappings_fkey_user FOREIGN KEY (user_id)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE comments (
  id UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  user_id UUID NOT NULL,
  text CHARACTER VARYING (200) NOT NULL,
  item_type CHARACTER VARYING (20) NOT NULL,  
  item_id UUID NOT NULL,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  CONSTRAINT comments_fkey_user FOREIGN KEY (user_id)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);         

CREATE trigger comments_creation_timestamp_trigger
   BEFORE INSERT ON comments
   for each row EXECUTE procedure creation_timestamp_handler();
   
CREATE trigger comments_updation_timestamp_trigger
   BEFORE UPDATE ON comments
   for each row EXECUTE procedure updation_timestamp_handler();


