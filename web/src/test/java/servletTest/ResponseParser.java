package servletTest;

public class ResponseParser {

  private ResponseStub response;

  public ResponseParser(ResponseStub response) {
    this.response = response;
  }

  public String getBody() {
    return this.response.getWriterOutput();
  }

  public String getRedirect() {
    return this.response.getRedirect();
  }

}
