import {createSlice} from '@reduxjs/toolkit';
const bleConfigSlice = createSlice({
  name: 'ble_config',
  initialState: {
    permissions: {
      bluetooth: false, //off
      location: false, //on
      locationPermission: false, //Access_Fine_location
    },
  },
  reducers: {
    setBluetooth: (state, {payload}) => {
      return {
        ...state,
        permissions: {
          ...state.permissions,
          bluetooth: payload,
        },
      };
    },
    setLocation: (state, {payload}) => {
      return {
        ...state,
        permissions: {
          ...state.permissions,
          location: payload,
        },
      };
    },
    setLocationPermission: (state, {payload}) => {
      console.log('newLocal..................');
      return {
        ...state,
        permissions: {
          ...state.permissions,
          locationPermission: payload,
        },
      };
    },
  },
});
// Action creators are generated for each case reducer function
export const {setLocation, setBluetooth, setLocationPermission} =
  bleConfigSlice.actions;

export default bleConfigSlice.reducer;
