# moonlight-api

REST API for Moonlight, an application that allows people to track changes in their mental wellbeing as they go through different physical fitness programmes.
This API is accessed by a React client. Users can sign up and join fitness programmes that consists of multiple stages. While going through these programmes, users will submit mental wellbeing surveys (one survey submission required for each stage) and at the end of the programme they will be able to see in their profile how this programme has affected their mental wellbeing. 

Features: 
- JWT user authentication.
- The application uses MongoDB as database.
- The API has CICD with Github Actions, it's containerised using Docker, and is currently deployed on AWS.
- There is a ReactJs + Redux client for this application but that has not yet been deployed. 

NOTE: There are some parts of the code where comments have been left out. They wouldn't have if it was an actual real-life production project :)

