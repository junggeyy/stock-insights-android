# Stock Insights: A Stock Analysis Mobile Application

## Project Overview

Stock Insights is an Android application that empowers users to track, analyze, and forecast stock market trends. The app integrates live financial data with machine learning predictions to deliver actionable insights for individual investors.

Built with a Kotlin frontend and Django backend, the app provides users with a seamless experience from real-time market browsing to detailed stock analysis and personalized stock management.

### Key Features

#### 1. Market Overview
- Real-time display of top stocks and market indices
- Quick access to stock information
- Intuitive list view of market performance

#### 2. Stock Search and Details
- Search stocks and view comprehensive information
- Access live stock price data and company details
- View historical candle charts

#### 3. AI - Powered Analysis
- 7-day stock price forecasting using Facebook Prophet
- Buy/Sell recommendations based on predicted trends

#### 4. Watchlist Stocks
- Add stocks to a personal watchlist

#### 5. User Authentication
- Secure login and signup with token-based authentication
- Personalized stock tracking experience

## Technical Architecture

### Frontend
- **Stack**: Kotlin, MVVM Architecture, Retrofit, LiveData, ViewModel, Hilt
- **Key Screens**:
  - Launch Page and Authentication Pages
  - Market Overview Page
  - Stock Detailed View
  - Stock Analysis Page
  - Watchlist Page
  - User Profile

### Backend
- Backend repository: https://github.com/2025-CSC-415/group-project-debuggers-backend
- **Technology**: Django, Django Rest Framework
- **Key Services**:
  - User Authentication
  - Stock Data Aggregation
  - Machine Learning Service
  - SQLite to store user account and stocks (Django ORM)
 
### ML Service
- **Model**: Facebook Prophet
- **Workflow**:
  - Model is trained on historical closing prices of last 5 years
  - Model generates a 14 days future forecast and we extract prediciton of next 7 days
  -  A simple recommendation system based on the 7-day forecast:
      - Buy if the price is expected to rise by 3% or more.
      - Sell if the price is expected to drop by 3% or more.
      - Hold otherwise.
  - A cleaned 7-day forecast is provided for chart visualization.

### APIs & Integrations
- **Finnhub API**: Real-time stock quotes and company information
- **Polygon.io API**: Historical candle chart data
- **yfinance**: Historical price data for ML
- **Facebook Prophet**: Stock price forecasting

![AndroidDev](https://github.com/user-attachments/assets/066d297f-8154-4b66-a7d0-c80da4c86486)



## Live Demo

[DEMO LINK](https://youtube.com/shorts/sa9yQm1N80U?feature=share)


