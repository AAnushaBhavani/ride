import { useState } from 'react';
import { 
  Box, 
  Container, 
  Typography, 
  Button, 
  Grid, 
  Paper, 
  TextField,
  useTheme,
  useMediaQuery,
  Fade
} from '@mui/material';
import { Link as RouterLink } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { 
  Search as SearchIcon,
  DirectionsCar as CarIcon,
  Person as PersonIcon,
  EmojiPeople,
  Speed,
  Security,
  ThumbUp,
  PhoneAndroid,
  LocalOffer
} from '@mui/icons-material';

const features = [
  {
    icon: <Speed fontSize="large" color="primary" />,
    title: 'Fast Matching',
    description: 'Quickly find rides or passengers with our smart matching algorithm.'
  },
  {
    icon: <Security fontSize="large" color="primary" />,
    title: 'Safe & Secure',
    description: 'Verified users and secure payments for your peace of mind.'
  },
  {
    icon: <ThumbUp fontSize="large" color="primary" />,
    title: 'Easy to Use',
    description: 'Simple and intuitive interface for booking and offering rides.'
  },
  {
    icon: <PhoneAndroid fontSize="large" color="primary" />,
    title: 'Mobile Friendly',
    description: 'Works seamlessly on all your devices, anywhere, anytime.'
  },
  {
    icon: <LocalOffer fontSize="large" color="primary" />,
    title: 'Best Prices',
    description: 'Competitive pricing that saves you money on every ride.'
  },
  {
    icon: <PersonIcon fontSize="large" color="primary" />,
    title: 'Community',
    description: 'Join thousands of happy riders and drivers in our community.'
  }
];

const howItWorks = [
  {
    step: 1,
    title: 'Create an Account',
    description: 'Sign up in minutes with your email and phone number.',
    icon: <EmojiPeople color="inherit" fontSize="large" />
  },
  {
    step: 2,
    title: 'Find or Offer a Ride',
    description: 'Search for rides or post your own trip details.',
    icon: <SearchIcon color="inherit" fontSize="large" />
  },
  {
    step: 3,
    title: 'Connect & Travel',
    description: 'Connect with fellow travelers and enjoy your journey!',
    icon: <CarIcon color="inherit" fontSize="large" />
  }
];

const HomePage = () => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('md'));
  const { isAuthenticated } = useAuth();
  const [searchQuery, setSearchQuery] = useState({
    from: '',
    to: '',
    date: new Date().toISOString().split('T')[0]
  });

  return (
    <Box sx={{ overflowX: 'hidden' }}>
      {/* Hero Section */}
      <Box
        sx={{
          background: `linear-gradient(135deg, ${theme.palette.primary.main} 0%, ${theme.palette.primary.dark} 100%)`,
          color: 'white',
          py: 12,
          position: 'relative',
          overflow: 'hidden'
        }}
      >
        <Container maxWidth="lg">
          <Grid container spacing={4} alignItems="center">
            <Grid item xs={12} md={6}>
              <Typography 
                variant={isMobile ? 'h4' : 'h3'} 
                component="h1" 
                gutterBottom 
                sx={{ fontWeight: 'bold' }}
              >
                Ride Smarter, Not Harder
              </Typography>
              <Typography 
                variant={isMobile ? 'h6' : 'h5'} 
                component="p" 
                gutterBottom
                sx={{ mb: 4, opacity: 0.9 }}
              >
                Find your perfect ride match. Save money, reduce emissions, and travel in comfort.
              </Typography>
              <Box sx={{ display: 'flex', gap: 2, mt: 4 }}>
                <Button
                  variant="contained"
                  color="secondary"
                  size="large"
                  component={RouterLink}
                  to={isAuthenticated ? '/book-ride' : '/register'}
                >
                  {isAuthenticated ? 'Book a Ride' : 'Get Started'}
                </Button>
                {!isAuthenticated && (
                  <Button
                    variant="outlined"
                    color="inherit"
                    size="large"
                    component={RouterLink}
                    to="/login"
                  >
                    Sign In
                  </Button>
                )}
              </Box>
            </Grid>
            <Grid item xs={12} md={6}>
              <Box
                component="img"
                src="/car-illustration.svg"
                alt="Car sharing"
                sx={{
                  width: '100%',
                  maxWidth: 500,
                  height: 'auto',
                  display: 'block',
                  mx: 'auto',
                  filter: 'drop-shadow(0 10px 20px rgba(0,0,0,0.2))'
                }}
              />
            </Grid>
          </Grid>
        </Container>
      </Box>

      {/* Search Section */}
      <Container maxWidth="md" sx={{ mt: -6, mb: 8, position: 'relative', zIndex: 1 }}>
        <Paper 
          component="form" 
          sx={{ 
            p: 3, 
            borderRadius: 2,
            boxShadow: 3
          }}
        >
          <Grid container spacing={2}>
            <Grid item xs={12} md={4}>
              <TextField
                fullWidth
                label="From"
                variant="outlined"
                value={searchQuery.from}
                onChange={(e) => setSearchQuery({...searchQuery, from: e.target.value})}
                InputProps={{
                  startAdornment: (
                    <SearchIcon color="action" sx={{ mr: 1 }} />
                  ),
                }}
              />
            </Grid>
            <Grid item xs={12} md={4}>
              <TextField
                fullWidth
                label="To"
                variant="outlined"
                value={searchQuery.to}
                onChange={(e) => setSearchQuery({...searchQuery, to: e.target.value})}
                InputProps={{
                  startAdornment: (
                    <SearchIcon color="action" sx={{ mr: 1 }} />
                  ),
                }}
              />
            </Grid>
            <Grid item xs={12} md={3}>
              <TextField
                fullWidth
                type="date"
                label="Date"
                variant="outlined"
                value={searchQuery.date}
                onChange={(e) => setSearchQuery({...searchQuery, date: e.target.value})}
                InputLabelProps={{
                  shrink: true,
                }}
              />
            </Grid>
            <Grid item xs={12} md={1}>
              <Button
                fullWidth
                variant="contained"
                color="primary"
                size="large"
                sx={{ height: '100%' }}
                component={RouterLink}
                to={`/rides?from=${searchQuery.from}&to=${searchQuery.to}&date=${searchQuery.date}`}
              >
                <SearchIcon />
                {!isMobile && 'Search'}
              </Button>
            </Grid>
          </Grid>
        </Paper>
      </Container>

      {/* Features Section */}
      <Container maxWidth="lg" sx={{ py: 8 }}>
        <Typography 
          variant="h4" 
          component="h2" 
          align="center" 
          gutterBottom
          sx={{ fontWeight: 'bold', mb: 6 }}
        >
          Why Choose Us
        </Typography>
        <Grid container spacing={4}>
          {features.map((feature, index) => (
            <Grid item xs={12} sm={6} md={4} key={index}>
              <Box
                sx={{
                  height: '100%',
                  display: 'flex',
                  flexDirection: 'column',
                  alignItems: 'center',
                  textAlign: 'center',
                  p: 3,
                  '&:hover': {
                    transform: 'translateY(-4px)',
                    transition: 'all 0.3s ease'
                  }
                }}
              >
                <Box
                  sx={{
                    width: 80,
                    height: 80,
                    borderRadius: '50%',
                    bgcolor: 'primary.light',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    mb: 3,
                    color: 'primary.contrastText'
                  }}
                >
                  {feature.icon}
                </Box>
                <Typography variant="h6" component="h3" gutterBottom>
                  {feature.title}
                </Typography>
                <Typography color="text.secondary">
                  {feature.description}
                </Typography>
              </Box>
            </Grid>
          ))}
        </Grid>
      </Container>

      {/* How It Works Section */}
      <Box sx={{ 
        bgcolor: 'background.paper',
        py: 8,
        borderTop: '1px solid',
        borderBottom: '1px solid',
        borderColor: 'divider'
      }}>
        <Container maxWidth="lg">
          <Box textAlign="center" mb={6}>
            <Typography 
              variant="h4" 
              component="h2" 
              gutterBottom 
              sx={{ fontWeight: 700 }}
            >
              How It Works
            </Typography>
            <Typography 
              variant="h6" 
              color="textSecondary" 
              sx={{ maxWidth: 700, mx: 'auto' }}
            >
              Get started with our platform in just a few simple steps
            </Typography>
          </Box>

          <Grid container spacing={4} sx={{ mt: 2 }}>
            {howItWorks.map((step, index) => (
              <Grid item xs={12} md={4} key={index}>
                <Fade in={true} timeout={1000} style={{ transitionDelay: `${index * 200}ms` }}>
                  <Box
                    sx={{
                      display: 'flex',
                      flexDirection: 'column',
                      alignItems: 'center',
                      textAlign: 'center',
                      p: 4,
                      height: '100%',
                      position: 'relative',
                      '&:not(:last-child):after': {
                        content: '"→"',
                        position: 'absolute',
                        right: -30,
                        top: '50%',
                        transform: 'translateY(-50%)',
                        fontSize: '2rem',
                        color: theme.palette.text.secondary,
                        opacity: 0.3,
                        display: isMobile ? 'none' : 'block',
                      },
                      '&:last-child:after': {
                        display: 'none',
                      },
                    }}
                  >
                    <Box
                      sx={{
                        width: 80,
                        height: 80,
                        borderRadius: '50%',
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                        bgcolor: 'primary.main',
                        color: 'white',
                        mb: 3,
                        fontWeight: 'bold',
                        fontSize: '1.5rem',
                        position: 'relative',
                      }}
                    >
                      {step.step}
                    </Box>
                    <Box
                      sx={{
                        width: 80,
                        height: 80,
                        borderRadius: '50%',
                        bgcolor: 'rgba(25, 118, 210, 0.1)',
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                        mb: 3
                      }}
                    >
                      {step.icon}
                    </Box>
                    <Typography variant="h6" component="h3" gutterBottom>
                      {step.title}
                    </Typography>
                    <Typography color="text.secondary">
                      {step.description}
                    </Typography>
                  </Box>
                </Fade>
              </Grid>
            ))}
          </Grid>
        </Container>
      </Box>

      {/* CTA Section */}
      <Container maxWidth="md" sx={{ py: 8, textAlign: 'center' }}>
        <Typography 
          variant="h4" 
          component="h2" 
          gutterBottom
          sx={{ fontWeight: 'bold', mb: 3 }}
        >
          Ready to Start Your Journey?
        </Typography>
        <Typography 
          variant="h6" 
          color="text.secondary" 
          sx={{ mb: 4, maxWidth: 700, mx: 'auto' }}
        >
          Join thousands of happy travelers who save money and reduce their carbon footprint with us.
        </Typography>
        <Button
          variant="contained"
          color="primary"
          size="large"
          component={RouterLink}
          to={isAuthenticated ? '/book-ride' : '/register'}
          sx={{ 
            px: 6, 
            py: 1.5,
            fontSize: '1.1rem',
            textTransform: 'none',
            borderRadius: 2
          }}
        >
          {isAuthenticated ? 'Book a Ride Now' : 'Sign Up for Free'}
        </Button>
      </Container>
    </Box>
  );
};

export default HomePage;