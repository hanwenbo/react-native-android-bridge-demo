/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
    Platform,
    StyleSheet,
    Text,
    View,
    DeviceEventEmitter
} from 'react-native';
import ToastExample from './ToastExample';
import MyLBS from './BaiduLbs'
import TTLock from './TTLock'

const instructions = Platform.select({
    ios: 'Press Cmd+R to reload,\n' +
        'Cmd+D or shake for dev menu',
    android: 'Double tap R on your keyboard to reload,\n' +
        'Shake or press menu button for dev menu',
});

export default class App extends Component<{}> {
    constructor(props) {
        super(props);
        this.state = {
            location: null,
        }
    }
    componentWillMount(){
        //监听事件名为EventName的事件
        DeviceEventEmitter.addListener('EventName', function() {

            alert("send success");
        });


    }
    async componentDidMount() {
        console.warn('console.error ==> Screen height is:22');
        TTLock.init()
        TTLock.requestBleEnable()
        TTLock.startBleService()
        TTLock.startBTDeviceScan()
        try {
            var result = await MyLBS.getTestString();
            this.setState({ location: result })
            console.warn(result)
          } catch (e) {
            console.error(e);
          }


    //    console.warn(MyLBS)
//            var result = await MyLBS.getTestString()
//            console.warn(result)
            MyLBS.startLocation((location) => {
                this.setState({ location: location })
            });
    }

    render() {
        ToastExample.show('Awesome', ToastExample.SHORT);
        if (this.state.location) {
            return <View><Text>
                {this.state.location}
            </Text>
                <Text style={styles.scan} onPress={()=>{
                    TTLock.init()
                    TTLock.requestBleEnable()
                    TTLock.startBleService()
                    TTLock.startBTDeviceScan()
                    ToastExample.show('扫描锁', ToastExample.SHORT);

                }}>扫描锁11</Text>
            </View>
        } else {
            return <View>
                <Text>你好!</Text>
            </View>
        }
        //   return (
        //   <View style={styles.container}>
        //     <Text style={styles.welcome}>
        //       Welcome to React Native!
        //     </Text>
        //     <Text style={styles.instructions}>
        //       To get started, edit App.js
        //     </Text>
        //     <Text style={styles.instructions}>
        //       {instructions}
        //     </Text>
        //   </View>
        // );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#F5FCFF',
    },
    welcome: {
        fontSize: 20,
        textAlign: 'center',
        margin: 10,
    },
    instructions: {
        textAlign: 'center',
        color: '#333333',
        marginBottom: 5,
    },
    scan:{
        backgroundColor: '#000',
        color:'#FFF'
    }
});
