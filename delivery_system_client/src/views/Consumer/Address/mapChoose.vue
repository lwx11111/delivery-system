<template>
    <el-row>
        <el-col :span="24">
            <el-input v-model="positionInput"
                      id="searchInputId"
                      class="common-layout"
                      size="small"
                      placeholder="输入关键词搜索位置"
                      style="height:25px;width:26%;margin-left:1%;"/>
            <el-button>搜索</el-button>
            <el-button type="primary" @click="submitPosition()">确定</el-button>
        </el-col>
    </el-row>
    <el-row style="margin-top: 10px; margin-bottom: 10px">
        <el-col>
            <div>
                选中的地址：{{data.position.detailAddress}}
            </div>
        </el-col>
    </el-row>


    <div class="gdmap-container">
        <div id="container"></div>
    </div>

</template>

<script lang="ts" setup>
import AMapLoader from "@amap/amap-jsapi-loader";
import { onMounted, onUnmounted, ref, reactive } from 'vue';
import { ElMessage } from "element-plus";
import {useStore} from "vuex";
import {useRouter} from "vue-router";
const store = useStore();
const router = useRouter()
const positionInput = ref('');

// Data
const data = reactive({
    positionInput:'',
    keyword:'',
    position:{
        longitude:'',
        latitude:'',
        cityName:'',
        cityId:'',
        provinceName:'',
        provinceId:'',
        countyName:'',
        countyId:'',
        detailAddress:'',
    }
})

const submitPosition = () => {
    if (data.position.detailAddress === '') {
        ElMessage.error('请选择地址');
        return;
    }
    sessionStorage.setItem('position', JSON.stringify(data.position))
    router.go(-1)
}

/**
 * 初始化地图
 */
const initMap = () => {
    window._AMapSecurityConfig = {
        securityJsCode: "e836ebf160efaeb0d8771d6d9e67bfba", // 密钥
    };
    AMapLoader.load({
        key: "643b466b2446d4e6d7d722f08cac560c", // 申请好的Web端开发者Key，首次调用 load 时必填
        version: "2.0", // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
        plugins: [
            'AMap.AutoComplete',
            'AMap.PlaceSearch',
            'AMap.Geocoder'
        ], // 需要使用的的插件列表
    }).then((AMap) => {
        var map = new AMap.Map("container",{
            resizeEnable: true,
            viewMode: "3D", // 是否为3D地图模式
            zoom: 8, // 初始化地图级别
            // center: [118,30], // 初始化地图中心点位置
        });

        // 地理编码插件
        var geocoder;
        AMap.plugin(["AMap.Geocoder"], function () {
            geocoder = new AMap.Geocoder({
                radius: 1000, //以已知坐标为中心点，radius为半径，返回范围内兴趣点和道路信息
                extensions: "all" //返回地址描述以及附近兴趣点和道路信息，默认“base”
            });
        });

        // 添加定位插件
        AMap.plugin(["AMap.Geolocation"], function () {
            var geo = new AMap.Geolocation({
                enableHighAccuracy: true,//是否使用高精度定位，默认:true
                timeout: 10000,          //超过10秒后停止定位，默认：5s
                zoomToAccuracy: true,   //定位成功后是否自动调整地图视野到定位点
                needAddress: true,
                extensions: 'all'
            })
            // 添加插件
            map.addControl(geo);

            // 定位当前位置
            geo.getCurrentPosition(<PositionCallback>function (status, result) {
                console.log(status);
                console.log(result);
                if (status === 'complete') {
                    // 获得详细地址
                    if (result.formattedAddress === ''){
                        geocoder.getAddress(new AMap.LngLat(result.position.lng, result.position.lat), function (status, result) {
                            if (status === 'complete' && result.regeocode) {
                                var address = result.regeocode.formattedAddress;
                                console.log('详细地址：', address);
                                data.position.detailAddress = address;
                            } else {
                                console.log('获取详细地址失败');
                            }
                        });
                    }
                    data.position.detailAddress = result.formattedAddress;
                    data.position.provinceName = result.addressComponent.province;
                    data.position.cityName = result.addressComponent.city;
                    data.position.cityId = result.addressComponent.citycode;
                    data.position.countyName = result.addressComponent.district;
                    data.position.countyId = result.addressComponent.adcode;
                    data.position.longitude = result.position.lng;
                    data.position.latitude = result.position.lat;
                } else {
                    ElMessage.error('定位失败');
                }
            });
            // 获取城市信息
            geo.getCityInfo(function (status, result) {
                console.log(status);
                console.log(result);
                if (status == 'complete') {
                    data.position.cityName = result.city;
                    data.position.cityId = result.citycode;
                    data.position.provinceName = result.province;
                } else {
                    ElMessage.error('获取城市失败');
                }
            });

            console.log(data.position)
        });

        // 搜索自动补全插件
        const auto = new AMap.AutoComplete({
            pageSize: 5, //每页结果数,默认10
            pageIndex: 1, //请求页码，默认1
            input: "searchInputId",
        });
        // 搜索插件
        const placeSearch = new AMap.PlaceSearch({
            map: map,
            panel: "panel",
            pageSize: 5, //每页结果数,默认10
            pageIndex: 1, //请求页码，默认1
        });
        auto.on("select",select);

        function select(e) {
            console.log(e)
            placeSearch.setCity(e.poi.adcode);
            placeSearch.search(e.poi.name, function (status, result){
                console.log(status);
                console.log(result);
            });  //关键字查询查询
        }
        // 点击搜索得到的结果
        placeSearch.on('markerClick', function (e) {
            // 处理用户点击地图标记的操作
            console.log(e);
            data.position.detailAddress = e.data.address;
            data.position.longitude = e.data.location.lng;
            data.position.latitude = e.data.location.lat;
            data.position.provinceName = e.data.pname;
            data.position.provinceId = e.data.pcode;
            data.position.cityName = e.data.cityname;
            data.position.cityId = e.data.citycode;
            data.position.countyName = e.data.adname;
            data.position.countyId = e.data.adcode;
        });

    })
}
onMounted(() => {
    console.log("onmounted");
    initMap();
});
onUnmounted(() => {
    // map?.destroy();
});

</script>

<style scoped>
.gdmap-container{
    width: 800px;
    height: 800px;
}
#container {
    padding: 0px;
    margin: 0px;
    border:1px solid gray;
    width: 100%;
    height: 100%;
}
</style>
