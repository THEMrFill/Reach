### Code example for Reach

Built using Kotlin, MVVM (ViewModels & databinding), Coroutines, Retrofit for API & Realm for storage.

### NOTE: Known error in execution
Because I was running on a tighter schedule than I'd ideally like, there is a known crash if you
go to the Cart page, change a quantity and then return to the home page.
To save time on the Realm storage I've done a "scrub & re-write" method that causes Realm data to 
be out of sync - given more time I would work on getting the storage more resilient.

I will continue working on the code to get it running properly, but it will be done after the
timeline of the test. 