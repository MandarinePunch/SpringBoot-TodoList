package com.rakku.todolist.service;

import com.rakku.todolist.model.TodoEntity;
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
    public TodoEntity add(TodoRequest request){
        TodoEntity todoEntity = new TodoEntity();

        todoEntity.setTitle(request.getTitle());
        todoEntity.setOrder(request.getOrder());
        todoEntity.setCompleted(request.getCompleted());

        return this.todoRepository.save(todoEntity);
    }

    // 특정 아이템 조회
    public TodoEntity searchById(Long id){
        return this.todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // 리스트 조회
    public List<TodoEntity> searchAll(){
        return this.todoRepository.findAll();
    }

    // 특정 아이템을 수정
    public TodoEntity updateById(Long id, TodoRequest request){
        TodoEntity todoEntity = this.searchById(id);

        if(request.getTitle() != null){
            todoEntity.setTitle(request.getTitle());
        }
        if(request.getOrder() != null){
            todoEntity.setOrder(request.getOrder());
        }
        if(request.getCompleted() != null){
            todoEntity.setCompleted(request.getCompleted());
        }

        return this.todoRepository.save(todoEntity);
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
