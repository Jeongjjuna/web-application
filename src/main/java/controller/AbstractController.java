package controller;

import request.HttpMethod;
import request.HttpRequest;
import response.HttpResponse;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        HttpMethod method = request.getMethod();

        if (method == HttpMethod.POST) {
            doPost(request, response);
        } else if (method == HttpMethod.GET) {
            doGet(request, response);
        }
    }

    protected void doPost(HttpRequest request, HttpResponse response) {
    }

    protected void doGet(HttpRequest request, HttpResponse response) {
    }
}
