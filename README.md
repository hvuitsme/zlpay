# ZLPay Demo Payment Integration

This project is a demo Android application that demonstrates integration with the [ZaloPay SDK](https://developer.zalopay.vn/) for processing payments. The app is built using Kotlin and follows a single-activity architecture with multiple fragments to manage navigation between screens (Home, Order Payment, and Payment Notification).

## Project Overview

The purpose of this demo project is to showcase how to:
- Initialize and configure the ZaloPay SDK.
- Create an order by sending a request to ZaloPay’s API.
- Launch the ZaloPay payment flow and handle callbacks (success, cancel, error) via deep linking.
- Use Android’s Navigation Component (without Safe Args in this version) to pass data between fragments manually through Bundles.
- Process the payment result and display a notification screen.

## Project Structure

- **MainActivity.kt**  
  Hosts the NavHostFragment and initializes the ZaloPay SDK.  
  Overrides `onNewIntent` to capture deep links from the ZaloPay app and navigates to the Payment Notification fragment accordingly.

- **Fragments**
  - **HomeFragment.kt**  
    Provides a UI for the user to input the number of items to purchase. It then calculates the total amount and navigates to the Order Payment screen.
  - **OrderPaymentFragment.kt**  
    Displays the order details and initiates the payment process by calling a custom API using a helper class that communicates with ZaloPay.
  - **PaymentNotificationFragment.kt**  
    Displays the result of the payment operation (success, cancellation, or error).

- **API & Helper Classes**
  - **AppInfo.kt**  
    Contains constants such as the App ID, MAC key, and the API endpoint URL.
  - **CreateOrder.kt**  
    Handles order creation by building a POST request with the required parameters and the computed MAC (hash) value.
  - **HttpProvider.kt**  
    Wraps OkHttp calls to send network requests.
  - **Helpers.kt, HMacUtil.kt, HexStringUtil.kt**  
    Provide utility functions for generating transaction IDs and computing HMAC for request validation.

## Requirements

- Android Studio 4.0 or higher
- Kotlin 1.4+ (or higher as configured)
- Minimum SDK Version: 21 or above
- Gradle configured with dependencies for:
  - [OkHttp](https://square.github.io/okhttp/) for network requests.
  - [ZaloPay SDK](https://developer.zalopay.vn/) for handling the payment process.
  - AndroidX Navigation Component (for single-activity, multiple fragments architecture).
  
Ensure that you have also included the necessary permissions in the AndroidManifest.xml (such as Internet access) and have set up deep linking correctly for the ZaloPay callback.

## Setup

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/yourusername/zlpay.git
   cd zlpay-demo
