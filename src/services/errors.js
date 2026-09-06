// src/services/errors.js
class DomainError extends Error {
  constructor(message, statusCode = 400) {
    super(message);
    this.name = 'DomainError';
    this.statusCode = statusCode;
  }
}

class NotFoundError extends DomainError {
  constructor(message) {
    super(message, 404);
    this.name = 'NotFoundError';
  }
}

module.exports = { DomainError, NotFoundError };
