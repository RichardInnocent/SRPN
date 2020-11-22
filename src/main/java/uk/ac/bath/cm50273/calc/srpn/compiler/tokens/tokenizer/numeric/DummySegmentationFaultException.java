package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.numeric;

public class DummySegmentationFaultException extends RuntimeException {

  public DummySegmentationFaultException() {
    super("Segmentation fault      (core dumped)");
  }

}
