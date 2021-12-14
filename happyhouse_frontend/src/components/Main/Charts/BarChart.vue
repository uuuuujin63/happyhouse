<script>
import { Bar } from "vue-chartjs";

export default {
  extends: Bar,
  props: {
    chartData: Array,
  },
  watch: {
    chartData() {
      this.$data._chart.update();
    },
  },
  mounted() {
    this.renderChart(
      {
        labels: this.chartData.map((data) => {
          return data.aptName;
        }),
        datasets: [
          {
            label: "조회수",
            backgroundColor: "#267819",
            data: this.chartData.map((data) => {
              return data.visited;
            }),
          },
        ],
      },
      {
        responsive: true,
        maintainAspectRatio: false,
        onClick: function (point, event) {
          let idx = event[0]._index;
        },
        scales: {
          yAxes: [
            {
              ticks: {
                beginAtZero: true,
              },
            },
          ],
        },
      }
    );
  },
};
</script>
