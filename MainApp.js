import {View, Text} from 'react-native';
import React from 'react';
import {useDispatch, useSelector} from 'react-redux';

export default function MainApp() {
  const {permissions: bleConfig} = useSelector(state => state.bleConfig);
  console.log(bleConfig);
  return (
    <View>
      <Text>
        Bluetooth is {bleConfig?.bluetooth ? 'on' : 'off'} and location is{' '}
        {bleConfig?.location ? 'on' : 'off'}, last permission is{' '}
        {bleConfig?.locationPermission ? 'on' : 'off'}
      </Text>
    </View>
  );
}
