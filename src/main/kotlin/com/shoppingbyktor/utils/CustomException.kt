package com.shoppingbyktor.utils

class UserNotExistException : Exception()
class EmailNotExist : Exception()
class PasswordNotMatch : Exception()
class FillInputCorrect : Exception()
class CommonException(itemName: String) : Exception(itemName)
