import React from 'react';
import logo from './logo.svg';
import './App.css';
import Inference from "./components/Inference";


function App() {
  return (
      <div className="container d-flex h-100 p-3 mx-auto flex-column">
        <header className="masthead mb-auto">
          <div className="inner">
            <h3 className="masthead-brand">Informatica</h3>
            <nav className="nav nav-masthead justify-content-center">
              <a className="nav-link active" href="#">Home</a>

            </nav>
          </div>
        </header>

        <main role="main" className="inner cover">
            <Inference/>
        </main>

        <footer className="mastfoot mt-auto">
          <div className="inner">
            <p>Maestría en Ciencias de la Información y las Comunicaciones <a
                href="https://twitter.com/mdo">@danyjavierb</a>.</p>
          </div>
        </footer>
      </div>
  );
}

export default App;
