package com.rakku.todolist.controller;

import com.rakku.todolist.model.TodoEntity;
import com.rakku.todolist.model.TodoRequest;
import com.rakku.todolist.model.TodoResponse;
import com.rakku.todolist.service.TodoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/")
public class TodoController {

    private final TodoService service;

    // Todo 추가
    @PostMapping
    public ResponseEntity<TodoResponse> create(@RequestBody TodoRequest request){
        log.info("CREATE");

        if(ObjectUtils.isEmpty(request.getTitle())){
            return ResponseEntity.badRequest().build();
        }

        if(ObjectUtils.isEmpty(request.getOrder())){
            request.setOrder(0L);
        }

        if(ObjectUtils.isEmpty(request.getCompleted())){
            request.setCompleted(false);
        }

        TodoEntity result = this.service.add(request);

        return ResponseEntity.ok(new TodoResponse(result));
    }

    // 특정 Todo 가져오기
    @GetMapping("{id}")
    public ResponseEntity<TodoResponse> readOne(@PathVariable Long id){
        log.info("READ ONE");

        TodoEntity result = this.service.searchById(id);

        return ResponseEntity.ok(new TodoResponse(result));
    }

    // 모든 Todo 가져오기
    @GetMapping
    public ResponseEntity<List<TodoResponse>> readAll(){
        log.info("READ ALL");

        List<TodoEntity> list = this.service.searchAll();
        List<TodoResponse> response = list.stream().map(TodoResponse::new)
                                                    .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // 특정 Todo update
    @PatchMapping("{id}")
    public ResponseEntity<TodoResponse> update(@PathVariable Long id, @RequestBody TodoRequest request){
        log.info("UPDATE");

        TodoEntity result = this.service.updateById(id, request);

        return ResponseEntity.ok(new TodoResponse(result));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id){
        log.info("DELETE ONE");

        this.service.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll(){
        log.info("DELETE ALL");

        this.service.deleteAll();

        return ResponseEntity.ok().build();
    }
}
