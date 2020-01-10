export GCLOUD_PROJECT_ID=jrebel-264617
export CI_PIPELINE_ID=__gcloud.json

gcloud config set project $GCLOUD_PROJECT_ID
gcloud auth activate-service-account --key-file $CI_PIPELINE_ID

SERVICE=http-server-header-dump-java
IMAGE=gcr.io/$GCLOUD_PROJECT_ID/$SERVICE
gcloud --quiet builds submit --tag $IMAGE
gcloud beta run deploy $SERVICE --image $IMAGE --platform managed --region us-central1 --allow-unauthenticated --memory 64M --quiet