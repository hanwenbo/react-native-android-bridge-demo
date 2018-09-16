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
            connect: false,
            lockMac: 'C4:54:12:32:3B:D8',
            lockVersion: {
                showAdminKbpwdFlag: true,
                groupId: 1,
                protocolVersion: 3,
                protocolType: 5,
                orgId: 1,
                logoUrl: "",
                scene: 2
            }
        }
    }

    componentWillMount() {
        let lockVersion = JSON.stringify(this.state.lockVersion)
        console.warn(lockVersion)
        // TTLock.unlockByUser(1700436704, JSON.stringify(lockVersion), 1537104120000, 1537190520000, 'MTE1LDExNywxMTUsMTEzLDEyMywxMTgsMTEzLDExMywxMTcsMTE1LDYx', 0, '4c,27,28,ae,66,82,39,b2,3b,bf,3d,41,78,d7,13,b7', 28800000)
        //监听事件名为EventName的事件
        DeviceEventEmitter.addListener('onFoundDevice', function (e) {
            console.warn(e)
            console.warn("发现锁")
        });
        DeviceEventEmitter.addListener('onDeviceConnected', function (e) {
            console.warn('链接成功')
            // 由于rn的类型比较局限 所以这里使用字符串代替int long 类型，为了解决长度问题
            TTLock.unlockByUser("1700436704", lockVersion, "1537104120000", "1537190520000", 'MTE1LDExNywxMTUsMTEzLDEyMywxMTgsMTEzLDExMywxMTcsMTE1LDYx', 0, '4c,27,28,ae,66,82,39,b2,3b,bf,3d,41,78,d7,13,b7', "28800000")
        });
        DeviceEventEmitter.addListener('onUnlock', function (e) {
            console.warn('开锁成功')
            alert("开锁成功");
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
        if (this.state.location) {
            return <View><Text>
                {this.state.location}
            </Text>
                <Text style={styles.scan} onPress={() => {
                    TTLock.init()
                    TTLock.requestBleEnable()
                    TTLock.startBleService()
                    TTLock.startBTDeviceScan()
                    ToastExample.show('扫描锁', 2);

                }}>扫描锁11</Text>
                <Text style={styles.scan} onPress={() => {
                    TTLock.connect(this.state.lockMac)
                    ToastExample.show('开锁', 2);

                }}>开锁</Text>
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
    scan: {
        backgroundColor: '#000',
        color: '#FFF'
    }
});
