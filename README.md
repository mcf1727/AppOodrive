# AppOodrive
Drive APP: Listing of files and folders / Navigate back and forth within folders

- How to change the server's IP address?

   In the file "MainActivity.kt", replace "localhost" in val API_URL = "http://localhost:8080" by the IP address of your established server  
   
- What dependencies your project links against?

    implementation 'androidx.cardview:cardview:1.0.0'
    
    implementation 'androidx.recyclerview:recyclerview:1.1.0'
    
    implementation 'com.squareup.retrofit2:retrofit:2.3.0'
    
    implementation 'com.squareup.retrofit2:converter-gson:2.3.0'
    

- Why have you choose these particular dependencies?

   cardview: to seperate the items in the recyclerView
   
   recyclerview: to show items dynamiquement on the screen to save the memory
   
   retrofit: to do Http request
   

- Any particular remarks you may have to express?

  TODO: adapt to screen rotation 
