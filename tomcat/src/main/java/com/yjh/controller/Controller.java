package com.yjh.controller;

import com.yjh.request.HttpRequest;
import com.yjh.response.HttpResponse;

public interface Controller {

    void service(HttpRequest request, HttpResponse response);
}
