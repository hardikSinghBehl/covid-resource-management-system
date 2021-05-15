package com.hardik.pomfrey.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "com.hardik.pomfrey.swagger")
public class OpenApiConfigurationProperties {

	private Properties properties = new Properties();

	@Data
	public class Properties {
		private String title;
		private String description;
		private String apiVersion;

		private Contact contact = new Contact();
		private Header header = new Header();
		private Security security = new Security();

		@Data
		public class Contact {
			private String email;
			private String name;
			private String url;
		}

		@Data
		public class Header {
			private String name;
			private String description;
		}

		@Data
		public class Security {
			private String name;
			private String scheme;
			private String bearerFormat;
		}
	}

}