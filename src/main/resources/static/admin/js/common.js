// 公共js
$(function () {
    // laydate 时间控件绑定
    if ($(".select-time").length > 0) {
        var startDate = laydate.render({
            elem: '#startTime',
            max: $('#endTime').val(),
            theme: 'molv',
            trigger: 'click',
            done: function (value, date) {
                // 结束时间大于开始时间
                if (value !== '') {
                    endDate.config.min.year = date.year;
                    endDate.config.min.month = date.month - 1;
                    endDate.config.min.date = date.date;
                } else {
                    endDate.config.min.year = '';
                    endDate.config.min.month = '';
                    endDate.config.min.date = '';
                }
            }
        });
        var endDate = laydate.render({
            elem: '#endTime',
            min: $('#startTime').val(),
            theme: 'molv',
            trigger: 'click',
            done: function (value, date) {
                // 开始时间小于结束时间
                if (value !== '') {
                    startDate.config.max.year = date.year;
                    startDate.config.max.month = date.month - 1;
                    startDate.config.max.date = date.date;
                } else {
                    startDate.config.max.year = '';
                    startDate.config.max.month = '';
                    startDate.config.max.date = '';
                }
            }
        });
    }
});