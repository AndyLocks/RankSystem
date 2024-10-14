package com.leute.rank_system.bot.exception;

public class KafkaSendMessageException extends RuntimeException {

  public KafkaSendMessageException(Throwable throwable) {
    super(throwable);
  }

  public KafkaSendMessageException(String message) {
    super(message);
  }
}
