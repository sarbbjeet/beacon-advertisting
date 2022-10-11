import {
  View,
  Text,
  Platform,
  PermissionsAndroid,
  NativeModules,
  Alert,
  AppRegistry,
} from 'react-native';
import {PERMISSIONS, requestMultiple} from 'react-native-permissions';
import DeviceInfo from 'react-native-device-info';
import BluetoothStateManager from 'react-native-bluetooth-state-manager';

import React, {useEffect, useState} from 'react';
import {BleManager} from 'react-native-ble-plx';
import {useDispatch, useSelector} from 'react-redux';

import {
  setBluetooth,
  setLocation,
  setLocationPermission,
} from '../redux/reducers/bleConfig';

const {LocationModule} = NativeModules;

export default function BleConfig({children}) {
  const [headless, setHeadless] = useState(false);
  const dispatch = useDispatch();
  const manager = new BleManager();

  const broadcastEvent = async data => {
    //gps or location enable/disable state
    const {location} = await data;
    if (location == 'off') {
      Alert.alert(
        'Turn on Location Permissions',
        'Press OK to turn on GPS Location',
        [
          {
            text: 'Cancel',
            onPress: () => console.log(''),
            style: 'cancel',
          },

          {
            text: 'OK',
            onPress: () => {
              LocationModule.toggleGpsButton();
              // BluetoothStateManager.openSettings();
              //turn on bluetooth
            },
          },
        ],
      );
      dispatch(setLocation(false));
    } else dispatch(setLocation(true));
  };

  const grantedBluetoothPermissions = async cb => {
    //event listener to check if Bluetooth state is active
    BluetoothStateManager.onStateChange(
      // BluetoothStateManager.EVENT_BLUETOOTH_STATE_CHANGE,
      async bluetoothState => {
        if (bluetoothState != 'PoweredOn') return dispatch(setBluetooth(false));
        return dispatch(setBluetooth(true));
      },
    );
    const state = await BluetoothStateManager.getState();
    if (state != 'PoweredOn') {
      if (Platform.OS === 'android') {
        BluetoothStateManager.requestToEnable();
        return;
      }
      Alert.alert(
        'Turn on Bluetooth Permissions',
        'Press OK to turn on Bluetooth',
        [
          {
            text: 'Cancel',
            onPress: () => console.log(''),
            style: 'cancel',
          },

          {
            text: 'OK',
            onPress: () => {
              BluetoothStateManager.openSettings();
              //turn on bluetooth
            },
          },
        ],
      );
      return;
    } else {
      dispatch(setBluetooth(true));
    }
  };
  const grantedLocationPermissions = async cb => {
    if (Platform.OS === 'android') {
      //const osVersion = await DeviceInfo?.getApiLevel();
      const osVersion = await DeviceInfo.getApiLevel();
      if (osVersion < 31) {
        const granted = await PermissionsAndroid.request(
          PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION,
          {
            title: 'Location Permission',
            message: 'Bluetooth Low Energy requires Locaiton',
            buttonNeutral: 'Ask Me Later',
            buttonNegative: 'Cancel',
            buttonPositive: 'OK',
          },
        );
        cb(granted === PermissionsAndroid.RESULTS.GRANTED);
      } else {
        const result = await requestMultiple([
          PERMISSIONS.ANDROID.BLUETOOTH_SCAN,
          PERMISSIONS.ANDROID.BLUETOOTH_CONNECT,
          PERMISSIONS.ANDROID.ACCESS_FINE_LOCATION,
        ]);

        const isGranted =
          // result['android.permission.BLUETOOTH_CONNECT'] ===
          //   PermissionsAndroid.RESULTS.GRANTED &&
          // result['android.permission.BLUETOOTH_SCAN'] ===
          //   PermissionsAndroid.RESULTS.GRANTED &&
          result['android.permission.ACCESS_FINE_LOCATION'] ===
          PermissionsAndroid.RESULTS.GRANTED;

        cb(isGranted);
      }
    } else {
      cb(true);
    }
  };
  useEffect(() => {
    const permissionGranted = async () => {
      await grantedLocationPermissions(async granted => {
        dispatch(setLocationPermission(granted));
      });

      await grantedBluetoothPermissions();
    };

    if (!headless) {
      setHeadless(true);
      AppRegistry.registerHeadlessTask('broadcastEvent', () => broadcastEvent);
    }

    permissionGranted();
    return () => manager.destroy();
  }, []);
  return <View>{children}</View>;
}
