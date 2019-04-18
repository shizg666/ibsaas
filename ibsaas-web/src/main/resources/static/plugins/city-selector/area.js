jQuery.fn.areaSelector = function () {
    let method = "init";
    let provinceid, cityid, districtid;
    let contentPrefix = "AreaContent";
    let setValueFlag = false;
    if (arguments.length > 0) {
        if (typeof(arguments[0]) === "string") {
            method = arguments[0];
        }
        if (arguments.length === 4) {
            provinceid = arguments[1];
            cityid = arguments[2];
            districtid = arguments[3];
            setValueFlag = true;
        }
    } else {
        return;
    }
    if (typeof(CITY_DATA) !== 'undefined' && CITY_DATA != null && CITY_DATA) {
        $.each($(this), function (i, item) {
            let $this = $(item);
            if (typeof($this.data("index")) === "undefined" || $this.data("index") === null) {
                $this.data("index", $(".area_content").length + 1);
            }
            let options = {
                level: $this.data("level"),
                prefix: $this.data("prefix"),
                id: $this.attr("id"),
                $input: $this.prop("readonly", true),
                contentId: contentPrefix + $this.data("index"),
                isPositioned: false
            };
            if (method === "init") {
                createAreaContent(options);
            }
            else if (method == "refresh") {
                let level = options.level;
                let contentId = options.contentId;
                options.$provinceInput = options.$input.siblings("input[name=" + options.prefix + "provinceid]");
                options.$cityInput = options.$input.siblings("input[name=" + options.prefix + "cityid]");
                options.$districtInput = options.$input.siblings("input[name=" + options.prefix + "districtid]");
                if (!setValueFlag) {
                    refreshAreaContent(options);
                } else {
                    //找到对应的id
                    if ((provinceid == null || provinceid == 0) && level == 1) {
                        refreshAreaContent(options);
                        return;
                    }
                    if ((cityid == null || provinceid == 0) && level == 2) {
                        refreshAreaContent(options);
                        return;
                    }
                    if ((provinceid == null || provinceid == 0) && level == 2) {
                        //需要找到匹配的provice
                        let $citydom = $("#" + contentId + " .data_city li[data-id=" + cityid + "]");
                        //如果已经渲染过城市了，直接找到
                        if ($citydom.length > 0) {
                            provinceid = $citydom.data("pid");
                        } else {
                            //城市还没有渲染过，需要在数据源中找到对应的城市
                            let cityData = JSON.stringify(CITY_DATA);
                            let idstr = '"id":' + cityid + ",";
                            let cityindex = cityData.indexOf(idstr);
                            if (cityindex >= 0) {
                                let str = cityData.substring(cityindex);
                                let pidindex = str.indexOf('"pid":');
                                str = str.substring(pidindex + 6);
                                let districtindex = str.indexOf(',"districts":');
                                str = str.substring(0, districtindex);
                                provinceid = str.trim();
                            }
                        }
                    }
                    if ((districtid == null || provinceid == 0) && level == 3) {
                        refreshAreaContent(options);
                        return;
                    }

                    if (provinceid != null && level >= 1) {
                        console.log("*****************1******" + provinceid);
                        $("#" + contentId + " .data_province li[data-id=" + provinceid + "]").trigger("click");
                        $("#" + contentId).removeClass("isBlock");
                    }
                    if (cityid != null && level >= 2) {
                        console.log("*****************2******" + cityid);
                        $("#" + contentId + " .data_city li[data-id=" + cityid + "]").trigger("click");
                        $("#" + contentId).removeClass("isBlock");
                    }
                    if (districtid != null && level >= 3) {
                        console.log("*****************3******" + districtid);
                        $("#" + contentId + " .data_district li[data-id=" + districtid + "]").trigger("click");
                        $("#" + contentId).removeClass("isBlock");
                    }
                }
            }
        })
    }

    //初始化地区选择框
    function createAreaContent(options) {

        options.$provinceInput = $(document.createElement("input")).attr("type", "hidden").attr("name", options.prefix + "provinceid");
        options.$cityInput = $(document.createElement("input")).attr("type", "hidden").attr("name", options.prefix + "cityid");
        options.$districtInput = $(document.createElement("input")).attr("type", "hidden").attr("name", options.prefix + "districtid");

        let html = '';
        html += ' <div class="area_content" id="' + options.contentId + '">';
        html += '<div class="area_menu">';
        html += '<ul>';
        html += '<li class="isClick">省份</li>';
        if (options.level > 1) {
            html += '<li>城市</li>';
        }
        if (options.level > 2) {
            html += '<li>县区</li>';
        }
        html += ' </ul>';
        html += '</div>';

        html += '<div class="area_data_content">';

        html += '<div class="area_data data_province isBlock">';
        html += '	<div class="data_Part part_one">';
        html += ' 	<a class="part_type">A-G</a>';
        html += ' 	<ul></ul>';
        html += ' </div>';
        html += ' <div class="data_Part part_two">';
        html += ' 	<a class="part_type">H-N</a>';
        html += ' 	<ul>';
        html += '	</ul>';
        html += ' </div>';
        html += ' <div class="data_Part part_three">';
        html += ' 	<a class="part_type">O-U</a>';
        html += ' 	<ul>';
        html += ' 	</ul>';
        html += ' </div>';
        html += ' <div class="data_Part part_four">';
        html += '	<a class="part_type part_four">V-Z</a>';
        html += '	<ul>';
        html += '	</ul>';
        html += ' </div>';
        html += '</div>  ';

        if (options.level > 1) {
            html += '<div class="area_data data_city">';
            html += '	<div class="data_Part part_one">';
            html += ' 	<a class="part_type">A-G</a>';
            html += ' 	<ul></ul>';
            html += ' </div>';
            html += ' <div class="data_Part part_two">';
            html += ' 	<a class="part_type">H-N</a>';
            html += ' 	<ul>';
            html += '	</ul>';
            html += ' </div>';
            html += ' <div class="data_Part part_three">';
            html += ' 	<a class="part_type">O-U</a>';
            html += ' 	<ul>';
            html += ' 	</ul>';
            html += ' </div>';
            html += ' <div class="data_Part part_four">';
            html += '	<a class="part_type part_four">V-Z</a>';
            html += '	<ul>';
            html += '	</ul>';
            html += ' </div>';
            html += '</div>';
        }
        if (options.level > 2) {
            html += '<div class="area_data data_district">';
            html += '	<div class="data_Part part_one">';
            html += ' 	<a class="part_type">A-G</a>';
            html += ' 	<ul></ul>';
            html += ' </div>';
            html += ' <div class="data_Part part_two">';
            html += ' 	<a class="part_type">H-N</a>';
            html += ' 	<ul>';
            html += '	</ul>';
            html += ' </div>';
            html += ' <div class="data_Part part_three">';
            html += ' 	<a class="part_type">O-U</a>';
            html += ' 	<ul>';
            html += ' 	</ul>';
            html += ' </div>';
            html += ' <div class="data_Part part_four">';
            html += '	<a class="part_type part_four">V-Z</a>';
            html += '	<ul>';
            html += '	</ul>';
            html += ' </div>';
            html += '</div>';
        }
        html += '</div></div>';
        html += '</div>';
        $('body').append(html);
        //提交元素

        let $input = options.$input;
        $input.addClass("area_input");
        $input.after(options.$provinceInput);
        if (options.level >= 2) {
            $input.after(options.$cityInput);
        }
        if (options.level >= 3) {
            $input.after(options.$districtInput);
        }
        // setCoordinate($input, options.contentId);
        createProvince(options);
        bindClick(options);
    }

    //更新
    function refreshAreaContent(options) {
        options.$input.val('');
        $('#' + options.contentId + ' .area_menu li:eq(0)').trigger('click');
        createProvince(options);

        $('#' + options.contentId + ' .data_city .part_one ul').html('');
        $('#' + options.contentId + ' .data_city .part_two ul').html('');
        $('#' + options.contentId + ' .data_city .part_three ul').html('');
        $('#' + options.contentId + ' .data_city .part_four ul').html('');

        $('#' + options.contentId + ' .data_district .part_one ul').html('');
        $('#' + options.contentId + ' .data_district .part_two ul').html('');
        $('#' + options.contentId + ' .data_district .part_three ul').html('');
        $('#' + options.contentId + ' .data_district .part_four ul').html('');

    }

    function bindClick(options) {
        options.$input.click(function () {
            $("#" + options.contentId).toggleClass('isBlock');
            setCoordinate(options);
        });
        //切换页签
        $('#' + options.contentId + ' .area_menu ul li').on('click', function () {
            $(this).addClass('isClick').siblings().removeClass('isClick');
            let thisIndex = $(this).index() + 1;
            $('#' + options.contentId + ' .area_data_content div:nth-child(' + thisIndex + ')').addClass('isBlock').siblings().removeClass('isBlock');
        });
        //失去焦点自动关闭
        $(document).click(function (e) {
            let $target = $(e.target);
            let $content = $("#" + options.contentId);
            if ($target[0] != options.$input[0] && $target[0] != $content[0] && $target.parents("#" + options.contentId)[0] != $content[0]) {
                if ($content.hasClass("isBlock")) {
                    $content.removeClass('isBlock');
                    //触发blur事件，自动校验（validationEngine）需要
                    options.$input.trigger("blur");
                }
            }
        })

    }

    //设置html位置
    function setCoordinate(options) {
        if (!options.isPositioned) {
            let offsetY = options.$input.offset().top || 0;
            let offsetX = options.$input.offset().left || 0;
            let myoffsetX = options.$input.data("offsetx") || 0;
            let myoffsetY = options.$input.data("offsety") || 0;
            $("#" + options.contentId).offset({top: offsetY + 34 + myoffsetY, left: offsetX + myoffsetX});
        }
    }

    //创建省份
    function createProvince(options) {
        let provinceDomPartOne = '';
        let provinceDomPartTwo = '';
        let provinceDomPartThree = '';
        let provinceDomPartFour = '';
        for (let i = 0; i < CITY_DATA.length; i++) {
            let province = CITY_DATA[i];
            options.province = province;
            let letter = makePy(province.name)[0].toUpperCase().charAt(0);
            switch (letter) {
                case "A":
                case "B":
                case "C":
                case "D":
                case "E":
                case "F":
                case "G":
                    provinceDomPartOne += '<li data-id="' + province.id + '"><a title="' + province.name + '">' + province.name + '<input value=' + province.id + ' style="display:none"></a></li>';
                    break;

                case "H":
                case "I":
                case "J":
                case "K":
                case "L":
                case "M":
                case "N":
                    provinceDomPartTwo += '<li data-id="' + province.id + '"><a title="' + province.name + '">' + province.name + '<input value=' + province.id + ' style="display:none"></a></li>';
                    break;

                case "O":
                case "P":
                case "Q":
                case "R":
                case "S":
                case "T":
                case "U":
                    provinceDomPartThree += '<li data-id="' + province.id + '"><a title="' + province.name + '">' + province.name + '<input value=' + province.id + ' style="display:none"></a></li>';
                    break;

                case "V":
                case "W":
                case "X":
                case "Y":
                case "Z":
                    provinceDomPartFour += '<li data-id="' + province.id + '"><a title="' + province.name + '">' + province.name + '<input value=' + province.id + ' style="display:none"></a></li>';
                    break;
            }
        }
        $('#' + options.contentId + ' .data_province .part_one ul').html(provinceDomPartOne);
        $('#' + options.contentId + ' .data_province .part_two ul').html(provinceDomPartTwo);
        $('#' + options.contentId + ' .data_province .part_three ul').html(provinceDomPartThree);
        $('#' + options.contentId + ' .data_province .part_four ul').html(provinceDomPartFour);
        provinceOperation(options);
    }

    function provinceOperation(options) {

        $('#' + options.contentId + ' .data_province li').unbind('click').bind('click', function () {
            options.provinceID = $(this).find('input').val();
            if (options.choosenProvice == null || options.choosenProvice != null && options.choosenProvice.id != options.provinceID) {
                for (let j = 0; j < CITY_DATA.length; j++) {
                    let province = CITY_DATA[j];
                    if (province.id == options.provinceID) {
                        options.choosenProvice = province;
                        break;
                    }
                }

            }
            //如果选中的与之前的值不一样
            if (options.$provinceInput.val() != options.provinceID) {
                options.$provinceInput.val(options.provinceID);
                options.$cityInput.val('');
                options.$districtInput.val('');
            }
            options.provinceName = $(this).children('a').text();
            options.$input.val(options.provinceName);
            options.$provinceInput.trigger('change');
            $("#" + options.contentId + " .data_province li").removeClass("area-active");
            $(this).addClass("area-active");
            console.log("=====================1=======" + options.choosenProvice.id);
            if (options.level === 1) {
                $("#" + options.contentId).toggleClass('isBlock');
                //触发blur事件，自动校验（validationEngine）需要
                options.$input.trigger("blur");
            } else {
                createCity(options);
                $('#' + options.contentId + ' .area_menu ul li:nth-child(2)').click();
            }
        });
    }

    //创建市
    function createCity(options) {
        let cityDomPartOne = '';
        let cityDomPartTwo = '';
        let cityDomPartThree = '';
        let cityDomPartFour = '';

        let cities = options.choosenProvice.cities;
        if (cities) {
            for (let k = 0; k < cities.length; k++) {
                let city = cities[k];
                let letter = makePy(city.name)[0].toUpperCase().charAt(0);
                switch (letter) {
                    case "A":
                    case "B":
                    case "C":
                    case "D":
                    case "E":
                    case "F":
                    case "G":
                        cityDomPartOne += '<li data-id="' + city.id + '" data-pid="' + city.pid + '"><a title="' + city.name + '">' + city.name + '<input value="' + city.id + '" style="display:none"></a></li>';
                        break;

                    case "H":
                    case "I":
                    case "J":
                    case "K":
                    case "L":
                    case "M":
                    case "N":
                        cityDomPartTwo += '<li data-id="' + city.id + '" data-pid="' + city.pid + '"><a title="' + city.name + '">' + city.name + '<input value="' + city.id + '" style="display:none"></a></li>';
                        break;

                    case "O":
                    case "P":
                    case "Q":
                    case "R":
                    case "S":
                    case "T":
                    case "U":
                        cityDomPartThree += '<li data-id="' + city.id + '" data-pid="' + city.pid + '"><a title="' + city.name + '">' + city.name + '<input value="' + city.id + '" style="display:none"></a></li>';
                        break;

                    case "V":
                    case "W":
                    case "X":
                    case "Y":
                    case "Z":
                        cityDomPartFour += '<li data-id="' + city.id + '" data-pid="' + city.pid + '"><a title="' + city.name + '">' + city.name + '<input value="' + city.id + '" style="display:none"></a></li>';
                        break;
                }
            }
        }


        $('#' + options.contentId + ' .data_city .part_one ul').html(cityDomPartOne);
        $('#' + options.contentId + ' .data_city .part_two ul').html(cityDomPartTwo);
        $('#' + options.contentId + ' .data_city .part_three ul').html(cityDomPartThree);
        $('#' + options.contentId + ' .data_city .part_four ul').html(cityDomPartFour);
        cityOperation(options);
    }

    function cityOperation(options) {
        $('#' + options.contentId + ' .data_city li').unbind('click').bind('click', function () {
            options.cityID = $(this).find('input').val();
            if (options.choosenCity == null || options.choosenCity != null && options.choosenCity.id != options.cityID) {
                let cities = options.choosenProvice.cities;
                for (let j = 0; j < cities.length; j++) {
                    let city = cities[j];
                    if (city.id == options.cityID) {
                        options.choosenCity = city;
                        break;
                    }
                }
            }
            //如果选中的与之前的值不一样
            if (options.$cityInput.val() != options.cityID) {
                options.$cityInput.val(options.cityID);
                options.$districtInput.val('');
            }
            options.cityName = $(this).children('a').text();
            options.$input.val(options.provinceName + ' / ' + options.cityName);
            options.$cityInput.trigger('change');
            $("#" + options.contentId + " .data_city li").removeClass("area-active");
            $(this).addClass("area-active");
            console.log("=====================2=======" + options.choosenCity.id);
            if (options.level === 2) {
                $("#" + options.contentId).toggleClass('isBlock');
                //触发blur事件，自动校验（validationEngine）需要
                options.$input.trigger("blur");
            } else {
                createDistrict(options);
                $('#' + options.contentId + ' .area_menu ul li:nth-child(3)').click();
            }
        })
    }

    //创建县区
    function createDistrict(options) {
        let districtDomPartOne = '';
        let districtDomPartTwo = '';
        let districtDomPartThree = '';
        let districtDomPartFour = '';

        let districts = options.choosenCity.districts;
        if (districts) {
            for (let l = 0; l < districts.length; l++) {
                let district = districts[l];
                let letter = makePy(district.name)[0].toUpperCase().charAt(0);
                switch (letter) {
                    case "A":
                    case "B":
                    case "C":
                    case "D":
                    case "E":
                    case "F":
                    case "G":
                        districtDomPartOne += '<li data-id="' + district.id + '" data-pid="' + district.pid + '"><a title="' + district.name + '">' + district.name + '<input value="' + district.id + '" style="display:none"></a></li>';
                        break;

                    case "H":
                    case "I":
                    case "J":
                    case "K":
                    case "L":
                    case "M":
                    case "N":
                        districtDomPartTwo += '<li data-id="' + district.id + '" data-pid="' + district.pid + '"><a title="' + district.name + '">' + district.name + '<input value="' + district.id + '" style="display:none"></a></li>';
                        break;

                    case "O":
                    case "P":
                    case "Q":
                    case "R":
                    case "S":
                    case "T":
                    case "U":
                        districtDomPartThree += '<li data-id="' + district.id + '" data-pid="' + district.pid + '"><a title="' + district.name + '">' + district.name + '<input value="' + district.id + '" style="display:none"></a></li>';
                        break;

                    case "V":
                    case "W":
                    case "X":
                    case "Y":
                    case "Z":
                        districtDomPartFour += '<li data-id="' + district.id + '" data-pid="' + district.pid + '"><a title="' + district.name + '">' + district.name + '<input value="' + district.id + '" style="display:none"></a></li>';
                        break;
                }
            }
        }

        $('#' + options.contentId + ' .data_district .part_one ul').html(districtDomPartOne);
        $('#' + options.contentId + ' .data_district .part_two ul').html(districtDomPartTwo);
        $('#' + options.contentId + ' .data_district .part_three ul').html(districtDomPartThree);
        $('#' + options.contentId + ' .data_district .part_four ul').html(districtDomPartFour);
        districtOperation(options);
    }

    function districtOperation(options) {
        $('#' + options.contentId + ' .data_district li').on('click', function () {
            $("#" + options.contentId).toggleClass('isBlock');
            options.$input.val(options.provinceName + ' / ' + options.cityName + ' / ' + $(this).children('a').text());
            $("#" + options.contentId + " .data_district li").removeClass("area-active");
            $(this).addClass("area-active");
            let districtid = $(this).find('a').find("input").val();
            if (options.$districtInput.val() != districtid) {
                options.$districtInput.val(districtid);
                options.$districtInput.trigger('change');
            }
            //触发blur事件，自动校验（validationEngine）需要
            options.$input.trigger("blur");
            console.log("=====================3=======" + districtid);
        })
    }

};

//初始化
$(function () {
    $(".ksudiCity").areaSelector("init");
});
