#!/bin/sh
##SBATCH --partition=general-compute
##SBATCH --partition=debug
#SBATCH --time=72:00:00
#SBATCH --nodes=1
#SBATCH --ntasks-per-node=1
#SBATCH --mem=4000
# Memory per node specification is in MB. It is optional. 
# The default limit is 3GB per core.
#SBATCH --constraint=CPU-E5645
## ^ 12 core dells (most common)
#SBATCH --job-name="ngonar_4_8_6_9_6"
#SBATCH --output=ngonar_6_test.out
#SBATCH --mail-user=thomasef@buffalo.edu
#SBATCH --mail-type=ALL
##SBATCH --requeue
#Specifies that the job will be requeued after a node failure.
#The default is that the job will not be requeued.


echo "SLURM_JOBID="$SLURM_JOBID
echo "SLURM_JOB_NODELIST"=$SLURM_JOB_NODELIST
echo "SLURM_NNODES"=$SLURM_NNODES
echo "SLURMTMPDIR="$SLURMTMPDIR

cd $SLURM_SUBMIT_DIR
echo "working directory = "$SLURM_SUBMIT_DIR

module load java/j2sdk/1.6.0_13

java -jar ngonar_optimize.jar 4 8 6 9 6 6
