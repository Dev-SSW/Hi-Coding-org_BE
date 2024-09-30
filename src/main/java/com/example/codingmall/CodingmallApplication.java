package com.example.codingmall;


import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CodingmallApplication {

	public static void main(String[] args) {
		// .env 파일을 로드
		Dotenv dotenv = Dotenv.configure().load();
		// 환경 변수에 값을 설정
		System.setProperty("SPRING_DATASOURCE_URL", dotenv.get("SPRING_DATASOURCE_URL"));
		System.setProperty("SPRING_DATASOURCE_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("SPRING_DATASOURCE_PASSWORD", dotenv.get("DB_PASSWORD"));
		System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
		System.setProperty("GOOGLE_CLIENT_ID", dotenv.get("GOOGLE_CLIENT_ID"));
		System.setProperty("GOOGLE_CLIENT_SECRET", dotenv.get("GOOGLE_CLIENT_SECRET"));
		System.setProperty("NAVER_CLIENT_ID", dotenv.get("NAVER_CLIENT_ID"));
		System.setProperty("NAVER_CLIENT_SECRET", dotenv.get("NAVER_CLIENT_SECRET"));
		SpringApplication.run(CodingmallApplication.class, args);
	}

}
