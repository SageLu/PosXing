// IRemoteService.aidl
package com.lt.aidl;

import com.lt.aidl.Person;

// Declare any non-default types here with import statements

interface IRemoteService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    /** Request the process ID of this service, to do evil things with it. */
    int getPid();

    /** get name by id */
    String getName(int id);

    /** get object by id */
    Person getPerson(int id);
}
