package com.rakku.todolist.service;

import com.rakku.todolist.model.TodoModel;
import com.rakku.todolist.model.TodoRequest;
import com.rakku.todolist.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoService {
    @Autowired
    private final TodoRepository todoRepository;

    // 아이템을 추가
    public TodoModel add(TodoRequest request){
        TodoModel todoModel = new TodoModel();

        todoModel.setTitle(request.getTitle());
        todoModel.setOrder(request.getOrder());
        todoModel.setCompleted(request.getCompleted());

        return this.todoRepository.save(todoModel);
    }

    // 특정 아이템 조회
    public TodoModel searchById(Long id){
        return this.todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // 리스트 조회
    public List<TodoModel> searchAll(){
        return this.todoRepository.findAll();
    }

    // 특정 아이템을 수정
    public TodoModel updateById(Long id, TodoRequest request){
        TodoModel todoModel = this.searchById(id);

        if(request.getTitle() != null){
            todoModel.setTitle(request.getTitle());
        }
        if(request.getOrder() != null){
            todoModel.setOrder(request.getOrder());
        }
        if(request.getCompleted() != null){
            todoModel.setCompleted(request.getCompleted());
        }

        return this.todoRepository.save(todoModel);
    }

    // 특정 아이템을 삭제
    public void deleteById(Long id){
        this.todoRepository.deleteById(id);
    }

    // 리스트 전체 목록 삭제
    public void deleteAll(){
        this.todoRepository.deleteAll();
    }
}
