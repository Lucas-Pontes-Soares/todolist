package br.com.lucassoares.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.lucassoares.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//toda a classe que o Spring vai gerenciar = @Component
@Component
public class FilterTaskAuth extends OncePerRequestFilter{

    @Autowired
    private IUserRepository iUserRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                
                // Pegar a autenticação (usuario e senha)
                var authorization = request.getHeader("Authorization");

                var authEncoded = authorization.substring("Basic".length()).trim();
                byte[] authDecode = Base64.getDecoder().decode(authEncoded);
                
                var authString = new String(authDecode);

                String[] credentials = authString.split(":");
                String username = credentials[0];
                String password = credentials[1];
                
                // Validar usuario
                    var user = this.iUserRepository.findByUsername(username);
                    if(user == null){
                        response.sendError(401);
                    }else {
                        // Validar senha
                        var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                        if(passwordVerify.verified){
                            filterChain.doFilter(request, response);
                        } else {
                            response.sendError(401);
                        }
                        
                        filterChain.doFilter(request, response);
                    }
    }
}
