# MonieBank - Digital Banking Platform

![GitHub branch check runs](https://img.shields.io/github/check-runs/GoogleCloudPlatform/bank-of-anthos/main)
[![Website](https://img.shields.io/website?url=https%3A%2F%2Fcymbal-bank.fsi.cymbal.dev%2F&label=live%20demo
)](https://cymbal-bank.fsi.cymbal.dev)

**MonieBank** is a sample HTTP-based web app that simulates a bank's payment processing network, allowing users to create artificial bank accounts, complete transactions, get savings recommendations and fraud detection analysis.

[//]: # (Google uses this application to demonstrate how developers can modernize enterprise applications using Google Cloud products, including: [Google Kubernetes Engine &#40;GKE&#41;]&#40;https://cloud.google.com/kubernetes-engine&#41;, [Anthos Service Mesh &#40;ASM&#41;]&#40;https://cloud.google.com/anthos/service-mesh&#41;, [Anthos Config Management &#40;ACM&#41;]&#40;https://cloud.google.com/anthos/config-management&#41;, [Migrate to Containers]&#40;https://cloud.google.com/migrate/containers&#41;, [Spring Cloud GCP]&#40;https://spring.io/projects/spring-cloud-gcp&#41;, [Cloud Operations]&#40;https://cloud.google.com/products/operations&#41;, [Cloud SQL]&#40;https://cloud.google.com/sql/docs&#41;, [Cloud Build]&#40;https://cloud.google.com/build&#41;, and [Cloud Deploy]&#40;https://cloud.google.com/deploy&#41;. This application works on any Kubernetes cluster.)

[//]: # ()
[//]: # (If you are using Bank of Anthos, please ★Star this repository to show your interest!)

[//]: # ()
[//]: # (**Note to Googlers:** Please fill out the form at [go/bank-of-anthos-form]&#40;https://goto2.corp.google.com/bank-of-anthos-form&#41;.)

## Screenshots

| Sign in                                                                                                   | Home                                                                                                    |
| ------------------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------ |
| [![Login](/docs/img/login.png)](/docs/img/login-edit.png) | [![User Transactions](/docs/img/transactions.png)](/docs/img/transactions.png) |


| Sign up                                               |
|-------------------------------------------------------|
| [![Signup](/docs/img/signup.png)](/docs/img/signup.png) |


## Service architecture

![Architecture Diagram](/docs/img/architecture.png)

| Service                                                     | Language      | Description                                                                                                                             |
|-------------------------------------------------------------|---------------|-----------------------------------------------------------------------------------------------------------------------------------------|
| [frontend](/src/frontend)                                   | Python        | Exposes an HTTP server to serve the website. Contains login page, signup page, and home page.                                           |
| [ledger-writer](/src/ledger/ledgerwriter)                   | Java          | Accepts and validates incoming transactions before writing them to the ledger.                                                          |
| [balance-reader](/src/ledger/balancereader)                 | Java          | Provides efficient readable cache of user balances, as read from `ledger-db`.                                                           |
| [transaction-history](/src/ledger/transactionhistory)       | Java          | Provides efficient readable cache of past transactions, as read from `ledger-db`.                                                       |
| [ledger-db](/src/ledger/ledger-db)                          | PostgreSQL    | Ledger of all transactions. Option to pre-populate with transactions for demo users.                                                    |
| [user-service](/src/accounts/userservice)                   | Python        | Manages user accounts and authentication. Signs JWTs used for authentication by other services.                                         |
| [contacts](/src/accounts/contacts)                          | Python        | Stores list of other accounts associated with a user. Used for drop down in "Send Payment" and "Deposit" forms.                         |
| [accounts-db](/src/accounts/accounts-db)                    | PostgreSQL    | Database for user accounts and associated data. Option to pre-populate with demo users.                                                 |
| [loadgenerator](/src/loadgenerator)                         | Python/Locust | Continuously sends requests imitating users to the frontend. Periodically creates new accounts and simulates transactions between them. |
| [investment-recommendation](/src/investment-recommendation) | NodeJs        | AI service that creates a detailed recommendation to users for them to invest in the right properties by interacting with germini-api.  |
| [vertext-ai](/src/investment-recommendation)                 | Java          | Interacts with vertex AI model to detect fraud of a given transaction                                                                   |

[//]: # (## Interactive quickstart &#40;GKE&#41;)

[//]: # ()
[//]: # (The following button opens up an interactive tutorial showing how to deploy Bank of Anthos in GKE:)

[//]: # ()
[//]: # ([![Open in Cloud Shell]&#40;https://gstatic.com/cloudssh/images/open-btn.svg&#41;]&#40;https://ssh.cloud.google.com/cloudshell/editor?show=ide&cloudshell_git_repo=https://github.com/GoogleCloudPlatform/bank-of-anthos&cloudshell_workspace=.&cloudshell_tutorial=extras/cloudshell/tutorial.md&#41;)

## Technologies Used : 
1. Google Kubernates Engine
2. Cloud SQL
3. CICD (CLoud Build / Cloud Deploy)
4. VertexAI
5. Cloud Run



# Overview
Moniebank is a modern digital banking platform that provides seamless, secure, and smart banking services. This application enables users to manage their finances, perform transactions, receive personalized investment recommendations, and benefit from fraud detection analysis.
Features

### User Authentication
   * Signup: Create a new account with personal details 
      * Username and password must contain 2-15 alphanumeric characters or underscores 
      * Required information: First name, Last name, Address, Country, State, NIN, and Birthday


   * Login: Secure access to your account
     * Remembers user information for convenient future logins


### Account Management
   * Account Dashboard: View your current balance and account information 
   * Transaction History: Track all your financial activities with detailed transaction logs

### Financial Services

   * Deposit Funds: Add money to your account using your account number 
   * Send Payment: Transfer funds to other users via their account numbers 
   * Investment Recommendations: Receive personalized investment advice based on your account balance

### Security Features

   * Fraud Detection: Automatic analysis of withdrawals to protect against fraudulent transactions 
   * Secure Authentication: Industry-standard security protocols to protect your account



### Getting Started
#### Prerequisites : 

1. Web browser with JavaScript enabled
2. Mobile device 
3. Internet connection


No installation required! Moniebank is a deployed application accessible via your browser.
Accessing the Platform

#### Visit http://35.193.246.153/

To Create an account or log in to an existing account

## User Guide

### Creating a New Account

1. Navigate to the signup page
2. Fill in all required fields:
   * Username (2-15 alphanumeric characters or underscores)
   * Password (2-15 alphanumeric characters or underscores)
   * Confirm Password
   * Personal details (Name, Address, Country, State, NIN, Birthday)


3. Click "Create Account"
4. Upon successful registration, you'll be automatically redirected to your dashboard

### Logging In

* Visit the login page 
* Enter your username and password 
* Optionally check "Remember information" for faster future logins 
* Click "Login"

### Making a Deposit

* From your dashboard, click "Deposit Funds"
* Enter the amount you wish to deposit 
* Follow the on-screen instructions to complete the deposit 
* Your transaction will appear in your transaction history

### Sending Money

* From your dashboard, click "Send Payment"
* Enter the recipient's account number 
* Enter the amount you wish to send 
* Review the transaction details 
* Confirm the payment

### Getting Investment Recommendations

* From your dashboard, click "Investment Recommendation"
* Review the personalized investment options based on your balance 
* Follow the provided guidance to optimize your financial growth

### Security
#### Fraud Detection

Moniebank automatically analyzes withdrawal requests to detect potential fraud:

* Unusual transaction patterns 
* Unusually large withdrawals

If suspicious activity is detected, you'll receive an alert and and advice accordingly

### Best Practices

* Never share your login credentials 
* Regularly check your transaction history 
* Log out after each session, especially on shared devices

### Technical Information
#### Supported Browsers

* Google Chrome (recommended)
* Mozilla Firefox 
* Safari 
* Microsoft Edge

#### Mobile Compatibility
Moniebank is fully responsive and works on all modern smartphones and tablets.

### Support

If you encounter any issues or have questions about using Moniebank:

Email: support@dreamdev-team5.com
Phone: +2348187626932


© 2025 Moniebank. All rights reserved.
Moniebank is not a real banking institution. This application is designed for demonstration purposes only.






## Steps to deploy to GKE

1. Ensure you have the following requirements:
   - [Google Cloud project](https://cloud.google.com/resource-manager/docs/creating-managing-projects#creating_a_project).
   - Shell environment with `gcloud`, `git`, and `kubectl`.

2. Clone the repository.

   ```sh
   git clone https://github.com/Maxiflexy/DreamDevs_Team_5
   cd bank-of-anthos/
   ```

3. Set the Google Cloud project and region and ensure the Google Kubernetes Engine API is enabled.

   ```sh
   export PROJECT_ID=<PROJECT_ID>
   export REGION=us-central1
   gcloud services enable container.googleapis.com \
     --project=${PROJECT_ID}
   ```

   Substitute `<PROJECT_ID>` with the ID of your Google Cloud project.

4. Create a GKE cluster and get the credentials for it using a given VPC network.

   ```sh
   gcloud compute networks list --project=dreamdev-team5
   
   gcloud container clusters create-auto monie-bank \
     --project=${PROJECT_ID} --region=${REGION} \ 
     --network=EXISTING_NETWORK_NAME
   ```

   Creating the cluster may take a few minutes.

5. Deploy Bank of Anthos to the cluster.

   ```sh
   kubectl apply -f ./extras/jwt/jwt-secret.yaml
   
   kubectl apply -f ./kubernetes-manifests
   ```

6. Wait for the pods to be ready and run the below to see the status.

   ```sh
   kubectl get pods
   ```

4. Wait for the pods to be ready.

   ```sh
   NAME                                  READY   STATUS    RESTARTS   AGE
   accounts-db-6f589464bc-6r7b7          1/1     Running   0          99s
   balancereader-797bf6d7c5-8xvp6        1/1     Running   0          99s
   contacts-769c4fb556-25pg2             1/1     Running   0          98s
   frontend-7c96b54f6b-zkdbz             1/1     Running   0          98s
   ledger-db-5b78474d4f-p6xcb            1/1     Running   0          98s
   ledgerwriter-84bf44b95d-65mqf         1/1     Running   0          97s
   loadgenerator-559667b6ff-4zsvb        1/1     Running   0          97s
   transactionhistory-5569754896-z94cn   1/1     Running   0          97s
   userservice-78dc876bff-pdhtl          1/1     Running   0          96s
   ```

7. Access the web frontend in a browser using the frontend's external IP.

   ```sh
   kubectl get service frontend | awk '{print $4}'
   ```

   Visit `http://EXTERNAL_IP` in a web browser to access your instance of Bank of Anthos.

8. Once you are done with it, delete the pods, statefulsets and or GKE cluster.

   ```sh 
   gcloud container clusters delete bank-of-anthos \
     --project=${PROJECT_ID} --region=${REGION}
   ```

   Deleting the cluster may take a few minutes.


9. To create artifact registry run :
   ```shell
      gcloud artifacts repositories create monieshop-repo \
        --repository-format=docker \
        --location=us-central1 \
        --description="Docker repository for GKE images"
   ```
   

