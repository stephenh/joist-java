package servletTest;


public class ResponseParser {

    private ResponseStub response;

    public ResponseParser(ResponseStub response) {
        this.response = response;
    }

    public String body() {
        return this.response.getWriterOutput();
    }

}
