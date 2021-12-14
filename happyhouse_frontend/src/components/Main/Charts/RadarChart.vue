<script>
import { Radar } from "vue-chartjs";

export default {
  extends: Radar,
  computed: {
    envInfos() {
      return this.$store.state.houseStore.envInfos;
    },
  },
  watch: {
    envInfos(newVal) {
      if (newVal && newVal.length === 4) {
        console.log(newVal[0].length);
        this.renderChart(
          {
            labels: ["학교", "어린이집/유치원", "병원", "학원"],
            datasets: [
              {
                label: "My First dataset",
                backgroundColor: "rgba(255,99,132,0.2)",
                borderColor: "rgba(255,99,132,1)",
                pointBackgroundColor: "rgba(255,99,132,1)",
                pointBorderColor: "#fff",
                pointHoverBackgroundColor: "#fff",
                pointHoverBorderColor: "rgba(255,99,132,1)",
                data: [
                  newVal[0].length,
                  newVal[0].length,
                  newVal[0].length,
                  newVal[0].length,
                ],
              },
            ],
          },
          { responsive: true, maintainAspectRatio: true }
        );
      }
    },
  },
};
</script>
