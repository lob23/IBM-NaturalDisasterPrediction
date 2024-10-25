# Replace this heading with your team/submission name

- [Project summary](#project-summary)
  - [The issue we are hoping to solve](#the-issue-we-are-hoping-to-solve)
  - [How our technology solution can help](#how-our-technology-solution-can-help)
  - [Our idea](#our-idea)
- [Technology implementation](#technology-implementation)
  - [IBM watsonx product(s) used](#ibm-ai-services-used)
  - [Solution architecture](#solution-architecture)
- [Presentation materials](#presentation-materials)
  - [Solution demo video](#solution-demo-video)
  - [Project development roadmap](#project-development-roadmap)
- [How to run the project](#how-to-run-the-project)

## Project summary

### The issue we are hoping to solve

As climate change progresses, the frequency of disasters such as storms, hurricanes, and floods is increasing, and these events are occurring over wider areas and regions. In some areas that rarely experience such disasters, there is a lack of experience regarding necessary preparations, leading to either insufficient readiness or overpreparation (e.g., purchasing more supplies than needed, which results in shortages for others). Therefore, we aim to develop a solution that provides information to help regions affected by disasters prepare more effectively.

### How our technology solution can help

Leveraging Watson.ai to determine necessities from user data and forecasts.

### Our idea

Climate change has had a serious impact on life today. In recent years, we have witnessed an increasing number of hurricanes and typhoons. As a Vietnamese citizen who has experienced worsening storms year after year, causing people to lose their homes, loved ones, and sometimes even entire communities, it is crucial to incorporate technology to assist those affected by these disasters. Additionally, raising awareness among those who have not yet been directly impacted is essential in addressing the current state of climate change.

We propose a mobile application, BeSafe, to provide necessary information for the users whenever a storm, hurricane or flood is coming. The app offers three primary functions. 

- First, it automatically alerts users if they are in a dangerous area that may be affected by a disaster. Specifically, whenever users open the app, it collects their geographical location (latitude and longitude) and sends it to the server. The server then assesses whether the user's location is at risk from a disaster and sends a notification with details about the event. We also recommend the nearest safe zone for users if they are in a warning zone (e.g., areas with a high likelihood of flooding or landslides). Moreover, the forecast will be cached on the user’s device to provide storm information in case there is no wireless connection due to infrastructure damage caused by the hurricane. Beyond disaster alerts, the application also serves as a daily reminder for users about adverse natural conditions that may occur on a given day, such as high wind speeds and heavy rainfall.

- Second, the app utilizes Watson.ai to provide personalized recommendations for users. We request that users input personal information about themselves and their families, including details such as gender, age, height, and weight. Once the personal data is submitted, the app automatically retrieves the weather forecast and predicts the timing of potential disaster impacts. Specifically, if a storm is anticipated, we will forecast not only the duration of the storm but also subsequent effects like flooding. Therefore, by considering the user's personal information and the timing of the predicted impacts, we input this data into Watson.ai's Meta-Llama model to generate predictions regarding necessary preparations and supplies. The results will be in very detail, in which they should purchase and the amount of the particular good.

- Third, we provide a community for support before and during the disaster. Whenever there is a storm or flood, the most necessary thing is to find a safe shelter. There are many individuals in need of shelter, as well as those willing to offer their spaces. The community serves as a connection between those seeking assistance and those providing it. Additionally, it facilitates raising awareness for contributions and volunteer efforts. By offering this application, we aim to assist users in dealing with climate change and natural disasters, reducing the potential severe impacts in both economic and safety aspects.


## Technology implementation

### IBM watsonx product(s) used

IBM Watson.ai helps predict and prepare essential supplies to overcome disasters. The process begins by providing the AI with weather forecasts. Users then input details about their family members, including personal health information such as height, weight, gender, age, and any specific needs like allergies or preferences. Watson.ai analyzes this data to generate a customized list of recommended necessities, including the quantity of food and specific goods tailored to each individual. Our prompt also emphasizes sourcing from local suppliers within each region, ensuring access to region-specific foods and materials. Additionally, special attention is given to the needs of vulnerable groups like women and children during storms and floods, focusing on essential personal items. The AI also considers each person's unique conditions to offer the most relevant recommendations.

### Solution architecture

Diagram and step-by-step description of the flow of our solution:

![SoftwareArchitecture](https://github.com/user-attachments/assets/d881b2e2-359f-482c-aa3d-2397e33488ce)

1. The user installs the application from the app store.
2. Whenever the app is opened, it sends the user's geographic location (latitude and longitude) from the client-side to the server.
3. The app retrieves storm data from OpenWeather and alerts users if a storm or hurricane is approaching. It also periodically monitors weather conditions by region, automatically sending pop-up notifications to users in affected areas, prompting them to check the app. 
3. When users navigate to the necessity prediction section, the app collects storm forecast data (e.g., duration of the storm and its aftermath, such as flooding) from OpenWeather, along with the user's and family members' personal details (height, weight, age, gender, etc.). It passes this information to Watson.ai, which calculates the essential supplies needed to survive the disaster. The output is region-specific, recommending different types of food that are suitable for the area.
4. In the community section, users can share their status and connect with others in need. The app facilitates communication between affected individuals and those offering help, with messaging handled through Firebase.

## Presentation materials

### Solution demo video

The video demo is posted to Youtube at the following URL Address: https://www.youtube.com/watch?v=IxoR1bwnu3E

### Project development roadmap

The project currently does the following things.

- Announcing users that storm/hurricane is coming.
- Predict and calculate a reasonable neccessity for users so that they won't miss anything or over-purchase that cause the neccessity run out for others who also need
- Community to connect between who need to help and who want to share. For example, if one need shelter and someone want to share the home, this feature is necessary.

In the future we plan to gather more data about the food by region to trend the model. For now, we leverage the pretrained model of Watson.ai. 

See below for our proposed schedule on next steps after Call for Code 2024 submission.

<img width="406" alt="RoadMap" src="https://github.com/user-attachments/assets/aa24298a-9af2-44c3-a58f-6df7992160b2">

## How to run the project

You have to run the project on separated sides: Server side and Client side.
For server, 
- npm install
- cd to function directory
- npm install
- npm run serve or npm run dev

For client,

