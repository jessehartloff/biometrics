import os

''' REFERENCE BATCH
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
#SBATCH --job-name="ngonsear_4_8_4_6_4"
#SBATCH --output=ngonsear_4_8_4_6_4_test.out
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

#module load intel/13.0
#module load intel-mpi/4.1.0
#module list
#ulimit -s unlimited
#

##gcc -o helloworld helloworld.c
##echo "Launch helloworld with srun"
##export I_MPI_PMI_LIBRARY=/usr/lib64/libpmi.so
##srun ./helloworld

module load java/j2sdk/1.6.0_13

java -jar ngonsear_optimize.jar 4 8 4 6 4 4
#
##echo "All Done!"
'''

prefix =  '''#!/bin/sh
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
'''

middle = '''
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

'''
for method in ['ngon', 'ngonar', 'ngonsear']:
	for n in [3,4,5,6,7]:
		batch = prefix
		batch += '#SBATCH --job-name="'+method+'_4_8_'+str(n)+'_'+str(n+3)+'_'+str(n)+'"'
		batch += '\n#SBATCH --output='+method+'_'+str(n)+'_test.out'
		batch += middle
		batch += 'java -jar '+method+'_optimize.jar 4 8 '+str(n)+' '+str(n+3)+' '+str(n)+' '+str(n)

		f = open(method+'_'+str(n)+'.sh', 'w')
		print >> f, batch
		f.close()		
