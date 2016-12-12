// IBookManager.aidl
package com.example.lenovo.myapplication2.book;

// Declare any non-default types here with import statements
import com.example.lenovo.myapplication2.book.Book;
import com.example.lenovo.myapplication2.book.IOnNewBookArrived;
interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    List<Book> getBooks();
    void addBook(in Book book);
    void register(IOnNewBookArrived listener);
    void unregister(IOnNewBookArrived listener);
}
