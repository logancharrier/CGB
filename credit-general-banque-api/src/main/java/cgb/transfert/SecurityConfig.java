package cgb.transfert;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import cgb.transfert.service.JwtService;
import cgb.transfert.service.MyUserDetailsService;



/*
 * 
 * Class qui peut surcharger la configuration de sécurité par défaut incluse 
 * dès que la dépendance spring-boot-starter-security est incluse.
 * Si vous ne souhaitez pas que Spring Security s'applique automatiquement, 
 * vous pouvez exclure spring-boot-starter-security de vos dépendances 
 * ou fournir une configuration qui désactive la sécurité 
 * (ce qui n'est généralement pas recommandé pour les applications de production).
 * Avec la config de sécurité par défaut, un password est automatiquement généré par spring boot 
 * pour pouvoir accéder à l'API avec le header suivant
 * 
 * Headers :
 * 	Content-Type: application/json
 * 	Authorization: Basic <votre_token_d_authentification>
 * 
 * avec : comme jeton, la concaténation de l'utilisateur et du mot de passe 
 * sous la forme user:password, le tout encodez en Base64.
 * 
 * Attention ce mécanisme de base n'inclus par de chiffrement http. 
 * 
 */
@Configuration
@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
@EnableMethodSecurity
public class SecurityConfig {


	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService, MyUserDetailsService userDetailsService) {
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
		filter.setJwtService(jwtService);
		filter.setUserDetailsService(userDetailsService);
		return filter;
	}


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http,
			JwtService jwtService,
			MyUserDetailsService userDetailsService) throws Exception {

		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
		jwtAuthenticationFilter.setJwtService(jwtService);
		jwtAuthenticationFilter.setUserDetailsService(userDetailsService);

		http.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(auth -> auth
				.requestMatchers(new AntPathRequestMatcher("/console/**")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/login/**")).permitAll()
				.anyRequest().authenticated())
		.formLogin(form -> form.disable())
		.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
		.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())); //Pour H2 en même frame
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}



	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
