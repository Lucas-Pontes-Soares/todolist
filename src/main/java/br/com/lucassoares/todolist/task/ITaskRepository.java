package br.com.lucassoares.todolist.task;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

//interface = modelo, temos os metodos sem implementação
//representação dos metodos
                                    //<qual classe esta representando, tipo de id>
public interface ITaskRepository extends JpaRepository<TaskModel, UUID>{

}
