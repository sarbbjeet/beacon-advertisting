import {View, Text, Button} from 'react-native';
import React, {useEffect} from 'react';
import BleConfig from './components/BleConfig';
import store from './redux/store';
import {Provider} from 'react-redux';
import MainApp from './MainApp';

export default function App() {
  // Bootstrap sequence functio
  useEffect(() => {}, []);

  return (
    <Provider store={store}>
      <BleConfig>
        <MainApp />
      </BleConfig>
    </Provider>
  );
}
