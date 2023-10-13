package br.com.lucassoares.todolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

//interface = modelo, temos os metodos sem implementação
//representação dos metodos
                                    //<qual classe esta representando, tipo de id>
public interface IUserRepository extends JpaRepository<UserModel, UUID>{
    UserModel findByUsername(String username);
}
